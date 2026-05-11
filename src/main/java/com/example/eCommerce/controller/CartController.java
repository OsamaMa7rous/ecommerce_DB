package com.example.eCommerce.controller;

import com.example.eCommerce.dto.cartDto.CartResponseDTO;
import com.example.eCommerce.entity.Cart;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.CartRepo;
import com.example.eCommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userID}")
    public ResponseEntity<CartResponseDTO> addCart(@PathVariable Long userID) {
       return ResponseEntity.ok(cartService.addCart(userID));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.deleteCart(id));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long id){
        return ResponseEntity.ok(cartService.getCartById(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCart() {
        return ResponseEntity.ok(cartService.getAllCart());
    }
}
