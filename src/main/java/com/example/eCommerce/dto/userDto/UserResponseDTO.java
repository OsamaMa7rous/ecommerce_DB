package com.example.eCommerce.dto.userDto;

import com.example.eCommerce.entity.Role;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;

}
