package com.example.eCommerce.controller;

import com.example.eCommerce.dto.cartItemDto.CartItemRequestDTO;
import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import com.example.eCommerce.service.CartItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CartItemResponseDTO createCartItem(@RequestBody CartItemRequestDTO item) {
        return cartItemService.createCartItem(item);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CartItemResponseDTO updateCartItem(@PathVariable Long id,@RequestBody CartItemRequestDTO item) {
        return cartItemService.updateCartItem(id, item);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        return cartItemService.deleteCartItem(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public CartItemResponseDTO getOneCartItem(@PathVariable Long id) {
        return cartItemService.getOneCartItem(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CartItemResponseDTO> getCartItems() {
        return cartItemService.getCartItems();
    }

}
