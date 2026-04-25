package com.example.eCommerce.dto.orderItemDto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
}
