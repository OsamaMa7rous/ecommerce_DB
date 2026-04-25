package com.example.eCommerce.dto.cartDto;

import com.example.eCommerce.dto.cartItemDto.CartItemResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {
private  Long id;
private List<CartItemResponseDTO> cartItemResponseDTOList;
private double totalPrice;
}
