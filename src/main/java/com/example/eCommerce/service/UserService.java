package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.UserMapper;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return userMapper.toDto(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = repository.getReferenceById(id);
        if(user!=null) {
            return userMapper.toDto(user);

        }
        else throw new RuntimeException("User not found");
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
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()))
                    );

        } else {
            throw new RuntimeException("User not Found");
        }


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
