package com.example.eCommerce.dto.orderItemDto;

import lombok.Data;

@Data
public class OrderItemRequestDto {


    private Long cartId;

    private Long orderId;

    private Long productId;

    private int quantity;
}

