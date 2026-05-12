package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.OrderMapper;
import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.OrderRepo;
import com.example.eCommerce.repository.ProductRepo;
import com.example.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderMapper orderMapper;


    public OrderResponseDTO createOrder(Order order) {
        User user = userRepo.findById(order.getUser().getId()).orElseThrow(() -> new RuntimeException("user not found"));
        order.setUser(user);
        double total = 0.0;

        for (OrderItem item : order.getOrderItem()) {
            Product product = productRepo.findById(item.getProduct().getId()).orElseThrow(() -> new RuntimeException("order item not found"));
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setOrder(order);
            total += item.getPrice() * item.getQuantity();

        }
        order.setTotalPrice(total);
        Order save = orderRepo.save(order);
        return orderMapper.orderToDto(save);
    }

    public OrderResponseDTO updateOrder(Long id, Order order) {
        Order oldOrder = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        User user = userRepo.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        oldOrder.setUser(user);

        oldOrder.setTotalPrice(order.getTotalPrice());
        oldOrder.getOrderItem().clear();
        oldOrder.getOrderItem().addAll(order.getOrderItem());
        for (OrderItem item : order.getOrderItem()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(item.getQuantity());
            item.setOrder(oldOrder);
            oldOrder.getOrderItem().add(item);
        }

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
