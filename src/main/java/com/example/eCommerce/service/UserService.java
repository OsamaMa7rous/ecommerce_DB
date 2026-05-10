package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.UserMapper;
import com.example.eCommerce.dto.userDto.LoginRequestDTO;
import com.example.eCommerce.dto.userDto.LoginResponseDTO;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repository;
    private final UserMapper userMapper;

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


}
