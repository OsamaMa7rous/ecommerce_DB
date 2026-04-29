package com.example.eCommerce.controller;

import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.register(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    /*
    @GetMapping("/auth/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal User  user) {
        return userService.getUserById()
    }
    */
    @GetMapping("/auth/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/auth/users")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());

    }


}
