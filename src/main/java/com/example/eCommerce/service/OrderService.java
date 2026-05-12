package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.OrderMapper;
import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;


    public OrderResponseDTO createOrder(Order order) {

        Order save = orderRepo.save(order);
        return orderMapper.orderToDto(save);
    }

    public OrderResponseDTO updateOrder(Long id, Order order) {
        Order oldOrder = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        oldOrder.setOrderItem(order.getOrderItem());
        oldOrder.setTotalPrice(order.getTotalPrice());
        oldOrder.setUser(oldOrder.getUser());
        Order save = orderRepo.save(oldOrder);
        return orderMapper.orderToDto(save);
    }

    public String deleteOrder(Long id) {
        Order save = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
        orderRepo.delete(save);

        return "Order Deleted successfully";

    }

    public OrderResponseDTO getOneOrder(Long id) {
        Order save = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
        return orderMapper.orderToDto(save);

    }

    public List<OrderResponseDTO> getOrders() {
        List<Order> all = orderRepo.findAll();
        return orderMapper.orderToDtoList(all);
    }

}
