package com.example.eCommerce.repository;

import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail( String email);
}
