package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.UserMapper;
import com.example.eCommerce.dto.userDto.LoginRequestDTO;
import com.example.eCommerce.dto.userDto.LoginResponseDTO;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo repository;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        UserDetails user =
                userDetailsService.loadUserByUsername(dto.getEmail());

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        if (repository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists in Service");
        }
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        repository.save(user);
        return userMapper.toDto(user);
    }

}
