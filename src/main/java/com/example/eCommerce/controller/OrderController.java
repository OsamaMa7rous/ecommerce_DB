package com.example.eCommerce.controller;

import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order Deleted successfully";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public OrderResponseDTO getOneOrder(@PathVariable Long id) {
        return orderService.getOneOrder(id);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<OrderResponseDTO> getOrders() {
       return orderService.getOrders();
    }


}
