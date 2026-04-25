package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemResponseDTO orderItemToDto(OrderItem orderItem) {
        if(orderItem == null)return null;
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setPrice(orderItem.getProduct().getPrice());
        dto.setQuantity(orderItem.getQuantity());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        return dto;
    }

    public List<OrderItemResponseDTO> orderItemToDtoList(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::orderItemToDto).toList();
    }


    public OrderResponseDTO orderToDto(Order order,List<OrderItem> orderItems) {
        if(order == null)return null;

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setOrderItemResponseDTOList(orderItemToDtoList(orderItems));
        dto.setOrderTotalPrice(order.getTotalPrice());
        return dto;
    }


}
