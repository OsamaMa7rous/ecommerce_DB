package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.OrderMapper;
import com.example.eCommerce.dto.orderItemDto.OrderItemRequestDto;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.Cart;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.repository.CartRepo;
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
    private final CartRepo cartRepo;
    private final OrderMapper orderMapper;


    public OrderItemResponseDTO createOrderItem(OrderItemRequestDto dto) {

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Cart cart = cartRepo.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Optional<OrderItem> optionalItem =
                orderItemRepo.findByOrderIdAndProductId(
                        dto.getOrderId(),
                        dto.getProductId()
                );

        OrderItem saved;

        if (optionalItem.isPresent()) {

            OrderItem oldItem = optionalItem.get();

            int oldQuantity = oldItem.getQuantity();
            int newQuantity = dto.getQuantity();

            int difference = newQuantity - oldQuantity;

            if (product.getStock() < difference) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            oldItem.setQuantity(newQuantity);
            oldItem.setProduct(product);
            oldItem.setCart(cart);
            oldItem.setPrice(product.getPrice());

            product.setStock(product.getStock() - difference);

            saved = orderItemRepo.save(oldItem);

        } else {

            if (product.getStock() < dto.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getName()
                );
            }

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());

            product.setStock(product.getStock() - dto.getQuantity());

            saved = orderItemRepo.save(item);
        }

        productRepo.save(product);

        updateTotalPrice(order);

        return orderMapper.orderItemToDto(saved);
    }
    public OrderItemResponseDTO updateOrderItem(Long id, OrderItem order) {
        OrderItem oldOrderItem = orderItemRepo.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Product product = productRepo.findById(order.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Order order1 = orderRepo.findById(order.getOrder().getId()).orElseThrow(() -> new RuntimeException("Order not found"));
        int oldQua = oldOrderItem.getQuantity();
        int newQua = order.getQuantity();
        int different = newQua - oldQua;
        if (different < 0) {
            product.setStock(product.getStock() + Math.abs(different));
        }
        if (different > 0) {
            product.setStock(product.getStock() - different);
        }
        if (product.getStock() < different && different > 0) {

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
