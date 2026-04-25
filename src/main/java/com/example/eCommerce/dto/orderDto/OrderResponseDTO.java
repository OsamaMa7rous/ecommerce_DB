package com.example.eCommerce.dto.orderDto;

import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long Id;
    private List<OrderItemResponseDTO> orderItemResponseDTOList;
    private BigDecimal orderTotalPrice;
    private String userEmail;
}
