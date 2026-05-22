package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.OrderMapper;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.repository.OrderItemRepo;
import com.example.eCommerce.repository.OrderRepo;
import com.example.eCommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;


    public OrderItemResponseDTO createOrderItem(OrderItem item) {
        Product product = productRepo.findById(item.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Order order = orderRepo.findById(item.getOrder().getId()).orElseThrow(() -> new RuntimeException("Order not found"));

        if (product.getStock() < item.getQuantity()) {
            throw new RuntimeException(
                    "Not enough stock for product: " + product.getName()
            );
        }

        Optional<OrderItem> existingItem = orderItemRepo.findByOrderIdAndProductId(item.getOrder().getId(), item.getProduct().getId());
        OrderItem save;
        if (existingItem.isPresent()) {
            OrderItem oldItem = existingItem.get();
            int newQuantity = oldItem.getQuantity() + item.getQuantity();
            if(product.getStock() < newQuantity) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }
            oldItem.setQuantity(newQuantity);
            oldItem.setPrice(product.getPrice());
             save = orderItemRepo.save(oldItem);

        } else {

            item.setOrder(order);
            item.setProduct(product);
            item.setPrice(product.getPrice());
             save = orderItemRepo.save(item);
        }


        product.setStock(product.getStock() - item.getQuantity());
        productRepo.save(product);

        updateTotalPrice(order);
        return orderMapper.orderItemToDto(save);
    }

    public OrderItemResponseDTO updateOrderItem(Long id, OrderItem order) {
        OrderItem oldOrderItem = orderItemRepo.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Product product = productRepo.findById(order.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Order order1 = orderRepo.findById(order.getOrder().getId()).orElseThrow(() -> new RuntimeException("Order not found"));
      int oldQua = oldOrderItem.getQuantity();
      int newQua = order.getQuantity();
      int different = newQua - oldQua;
      if(different < 0) {
          product.setStock(product.getStock() + Math.abs(different));
      }
        if (different > 0) {
            product.setStock(product.getStock() - different);
        }
        if (product.getStock() < order.getQuantity()) {

            throw new RuntimeException(
                    "Not enough stock for product: " + product.getName()
            );
        }
        oldOrderItem.setOrder(order1);
        oldOrderItem.setQuantity(order.getQuantity());
        oldOrderItem.setPrice(product.getPrice());
        oldOrderItem.setProduct(product);
        OrderItem save = orderItemRepo.save(oldOrderItem);
        updateTotalPrice(save.getOrder());
        return orderMapper.orderItemToDto(save);
    }

    public String deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepo.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Product product = orderItem.getProduct();
        product.setStock(orderItem.getQuantity() + product.getStock());
        productRepo.save(product);
        Order order = orderItem.getOrder();
        orderItemRepo.delete(orderItem);
        updateTotalPrice(order);
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

    private void updateTotalPrice(Order order) {
        double price = order.getOrderItem().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        order.setTotalPrice(price);
        orderRepo.save(order);

    }

}
