package com.example.eCommerce.controller;

import com.example.eCommerce.dto.orderItemDto.OrderItemRequestDto;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public OrderItemResponseDTO createOrderItem(@RequestBody OrderItemRequestDto item) {
        return orderItemService.createOrderItem(item);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OrderItemResponseDTO updateOrderItem(@PathVariable Long id,@RequestBody OrderItem order) {
     return orderItemService.updateOrderItem(id, order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return "OrderItem Deleted successfully";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public OrderItemResponseDTO getOneOrderItem(@PathVariable Long id) {
        return orderItemService.getOneOrderItem(id);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<OrderItemResponseDTO> getOrderItems() {
        return orderItemService.getOrderItems();

    }


}
