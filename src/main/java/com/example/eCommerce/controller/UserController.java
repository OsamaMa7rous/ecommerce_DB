package com.example.eCommerce.controller;

import com.example.eCommerce.dto.userDto.LoginRequestDTO;
import com.example.eCommerce.dto.userDto.LoginResponseDTO;
import com.example.eCommerce.dto.userDto.UserRequestDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.register(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(userService.login(dto));

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());

    }


}
