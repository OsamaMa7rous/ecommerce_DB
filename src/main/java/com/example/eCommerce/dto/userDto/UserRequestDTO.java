package com.example.eCommerce.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

}
