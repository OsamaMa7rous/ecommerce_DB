package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.CartMapper;
import com.example.eCommerce.dto.cartItemDto.CartItemRequestDTO;
import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import com.example.eCommerce.entity.Cart;
import com.example.eCommerce.entity.CartItem;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.repository.CartItemRepo;
import com.example.eCommerce.repository.CartRepo;
import com.example.eCommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    public CartItemResponseDTO createCartItem(CartItemRequestDTO item) {
        Cart cart = cartRepo.findById(item.getCart_id()).orElseThrow(() -> new RuntimeException("CartItem not found"));
        Product product = productRepo.findById(item.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartMapper.toCartItem(item, product, cart);
        CartItem save = cartItemRepo.save(cartItem);
        return cartMapper.toCartItemDto(save);

    }

    public CartItemResponseDTO updateCartItem(Long id, CartItemRequestDTO item) {
        CartItem oldCartItem = cartItemRepo.findById(id).orElseThrow(() -> new RuntimeException("CartItem not found"));
        Cart cart = cartRepo.findById(item.getCart_id()).orElseThrow(() -> new RuntimeException("CartItem not found"));
        Product product = productRepo.findById(item.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        oldCartItem.setCart(cart);
        oldCartItem.setQuantity(item.getQuantity());
        oldCartItem.setProduct(product);
        CartItem save = cartItemRepo.save(oldCartItem);
        return cartMapper.toCartItemDto(save);
    }

    public String deleteCartItem(Long id) {
        CartItem item = cartItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepo.delete(item);
        return "CartItem deleted successfully";
    }

    public CartItemResponseDTO getOneCartItem(Long id) {
        CartItem item = cartItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        return cartMapper.toCartItemDto(item);
    }

    public List<CartItemResponseDTO> getCartItems() {
        return cartItemRepo.findAll().stream().map(cartMapper::toCartItemDto).toList();
    }


}
