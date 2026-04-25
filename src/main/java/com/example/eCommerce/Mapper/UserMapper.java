package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.productDto.ProductRequestDTO;
import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole("USER");
        return user;
    }


    public UserResponseDTO toDto(User user) {
        UserResponseDTO newUser = new UserResponseDTO();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setRole(user.getRole());
        newUser.setId(user.getId());
        return newUser;
    }


}
