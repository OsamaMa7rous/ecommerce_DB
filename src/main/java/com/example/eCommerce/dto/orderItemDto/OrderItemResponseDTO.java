package com.example.eCommerce.dto.orderItemDto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Long orderId;
    private double price;
    private int quantity;
}
