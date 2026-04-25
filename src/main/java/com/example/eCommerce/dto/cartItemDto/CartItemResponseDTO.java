package com.example.eCommerce.dto.cartItemDto;

import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.entity.Cart;
import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;
    private ProductResponseDTO product;
    private int quantity;
}
