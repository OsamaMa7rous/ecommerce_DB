package com.example.eCommerce.service;

import com.example.eCommerce.dto.userDto.LoginRequestDTO;
import com.example.eCommerce.dto.userDto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserDetailsService userDetailsService;

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

}
