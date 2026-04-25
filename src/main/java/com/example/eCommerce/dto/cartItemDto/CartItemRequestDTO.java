package com.example.eCommerce.dto.cartItemDto;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Long product_id;
    private int quantity;
}
