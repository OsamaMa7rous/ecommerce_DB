package com.example.eCommerce.service;

import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.repository.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemItemService {
    private final OrderItemRepo orderItemRepo;


    public OrderItemResponseDTO createOrderItem(OrderItem item) {


    }

    public OrderItemResponseDTO updateOrderItem(Long id, OrderItem
            order) {

    }

    public String deleteOrderItem(Long id) {

        return "OrderItem Deleted successfully";

    }

    public OrderItemResponseDTO getOneOrderItem(Long id) {


    }

    public List<OrderItemResponseDTO> getOrderItems() {

    }

}
