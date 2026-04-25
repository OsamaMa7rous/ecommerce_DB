package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.cartDto.CartResponseDTO;
import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import com.example.eCommerce.dto.userDto.UserResponseDTO;
import com.example.eCommerce.entity.Cart;
import com.example.eCommerce.entity.CartItem;
import com.example.eCommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemResponseDTO toCartItemDto(CartItem cartItems) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(cartItems.getId());
        dto.setQuantity(cartItems.getQuantity());
        dto.setProduct(ProductMapper.toDto(cartItems.getProduct()));
        return dto;

    }

    public List<CartItemResponseDTO> toCartItemDtoList(List<CartItem> cartItems) {
        return cartItems.stream().map(this::toCartItemDto).toList();

    }



    public CartResponseDTO toCartDto(Cart cart, List<CartItem> cartItems) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setCartItemResponseDTOList(toCartItemDtoList(cartItems));
        double totalPrice = cartItems.stream().mapToDouble(e -> e.getProduct().getPrice()*e.getQuantity()).sum();
        dto.setTotalPrice(totalPrice);
        return dto;
    }


}
