package com.example.eCommerce.dto.userDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

}
