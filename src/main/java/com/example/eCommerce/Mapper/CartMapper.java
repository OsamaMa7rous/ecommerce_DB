package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.cartDto.CartResponseDTO;
import com.example.eCommerce.dto.cartItemDto.CartItemRequestDTO;
import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import com.example.eCommerce.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartItemResponseDTO toCartItemDto(CartItem cartItems) {
        if (cartItems == null) return null;

        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(cartItems.getId());
        dto.setQuantity(cartItems.getQuantity());
        dto.setProduct(ProductMapper.toDto(cartItems.getProduct()));
        return dto;

    }

    public CartItem toCartItem(CartItemRequestDTO dto, Product product, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        return cartItem;
    }

    public OrderItem toOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setProduct(cartItem.getProduct());
        return orderItem;
    }


    public List<CartItemResponseDTO> toCartItemDtoList(List<CartItem> cartItems) {
        if (cartItems == null) return null;

        return cartItems.stream().map(this::toCartItemDto).toList();

    }


    public CartResponseDTO toCartDto(Cart cart, List<CartItem> cartItems) {
        if (cartItems == null) return null;

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setCartItemResponseDTOList(toCartItemDtoList(cartItems));
        double totalPrice = cartItems.stream().mapToDouble(e -> e.getProduct().getPrice() * e.getQuantity()).sum();
        dto.setTotalPrice(totalPrice);
        return dto;
    }


}
