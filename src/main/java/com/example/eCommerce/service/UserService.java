package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.UserMapper;
import com.example.eCommerce.dto.userDto.LoginRequestDTO;
import com.example.eCommerce.dto.userDto.LoginResponseDTO;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/*
 @RequiredArgsConstructor
 بتعمل عمل ال @ِAutoWired عن طريق استخدام final
      private final UserRepo repository;


   */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        if (repository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists in Service");
        }
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        repository.save(user);
        return userMapper.toDto(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = repository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Invalid Email or Password when getting the user by email"));
        System.out.println(user.toString());

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Email or Password when comparing passwords");
        }
        return new LoginResponseDTO(user.getId(), user.getEmail(), "Login successful");
    }

    public UserResponseDTO getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return userMapper.toDto(user);

    }

    public List<UserResponseDTO> getAllUsers() {

        List<User> users = repository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = findByEmail(email);
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));


    }

    public UserRepo getRepository() {
        return repository;
    }

    public void setRepository(UserRepo repository) {
        this.repository = repository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
