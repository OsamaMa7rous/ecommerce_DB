package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {


    public OrderItemResponseDTO orderItemToDto(OrderItem orderItem) {
        if (orderItem == null) return null;
        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        dto.setOrderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null);
        dto.setProductId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null);
        dto.setProductName(orderItem.getProduct() != null ? orderItem.getProduct().getName() : null);
        dto.setOrderItemId(orderItem.getId());
        dto.setPrice(orderItem.getPrice());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }

    public List<OrderItemResponseDTO> orderItemToDtoList(List<OrderItem> orderItems) {
        if (orderItems == null) return List.of();
        return orderItems.stream().map(this::orderItemToDto).toList();
    }

    public OrderItem orderItemDtoToEntity(OrderItemResponseDTO dto) {
        if (dto == null) return null;
        OrderItem orderItem = new OrderItem();

        orderItem.getProduct().setId(dto.getProductId());
        orderItem.getProduct().setName(dto.getProductName());
        orderItem.setPrice(dto.getPrice());
        orderItem.getOrder().setId(dto.getOrderId());
        orderItem.setQuantity(dto.getQuantity());
        return orderItem;
    }

    public List<OrderItem> orderItemsDtoListToEntityList(List<OrderItemResponseDTO> orderItems) {
        return orderItems.stream().map(this::orderItemDtoToEntity).toList();
    }


    public OrderResponseDTO orderToDto(Order order) {
        if (order == null) return null;

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());

        dto.setUserEmail(order.getUser() != null ? order.getUser().getEmail() : null);
        dto.setOrderItemResponseDTOList(orderItemToDtoList(order.getOrderItem()));
        dto.setOrderTotalPrice(order.getTotalPrice());
        return dto;
    }

    public List<OrderResponseDTO> orderToDtoList(List<Order> orders) {
        if (orders == null) return null;
        return orders.stream().map(this::orderToDto).toList();
    }




}
