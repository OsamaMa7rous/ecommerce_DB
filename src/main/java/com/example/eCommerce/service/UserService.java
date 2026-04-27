package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.UserMapper;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
/*
 @RequiredArgsConstructor
 بتعمل عمل ال @ِAutoWired عن طريق استخدام final
      private final UserRepo repository;


   */
public class UserService {

    @Autowired
    private UserRepo repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        if (repository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists in Service");
        }
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return userMapper.toDto(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = repository.getReferenceById(id);
        if (user == null) {
            throw new RuntimeException("User Not found in Service");
        }
        return userMapper.toDto(user);
    }

    public List<UserResponseDTO> getAllUsers() {

        List<User> users = repository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    public UserResponseDTO findByEmail(String email) {
        UserResponseDTO user =repository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User Not Found in Service");
        }
        return user;

    }


}
