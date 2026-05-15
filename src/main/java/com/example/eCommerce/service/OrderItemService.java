package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.OrderMapper;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.repository.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepo orderItemRepo;
    private final OrderMapper orderMapper;


    public OrderItemResponseDTO createOrderItem(OrderItem item) {
        OrderItem save = orderItemRepo.save(item);
        return orderMapper.orderItemToDto(save);
    }

    public OrderItemResponseDTO updateOrderItem(Long id, OrderItem order) {
        OrderItem oldOrderItem = orderItemRepo.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        oldOrderItem.setQuantity(order.getQuantity());
        oldOrderItem.setPrice(order.getPrice());
        oldOrderItem.setProduct(order.getProduct());
        orderItemRepo.save(oldOrderItem);
        return orderMapper.orderItemToDto(oldOrderItem);
    }

    public String deleteOrderItem(Long id) {
        orderItemRepo.deleteById(id);
        return "OrderItem Deleted successfully";

    }

    public OrderItemResponseDTO getOneOrderItem(Long id) {
        OrderItem orderItem = orderItemRepo.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        return orderMapper.orderItemToDto(orderItem);
    }

    public List<OrderItemResponseDTO> getOrderItems() {
        List<OrderItem> all = orderItemRepo.findAll();
        return orderMapper.orderItemToDtoList(all);
    }

}
