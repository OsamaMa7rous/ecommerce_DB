package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.orderDto.OrderResponseDTO;
import com.example.eCommerce.dto.orderItemDto.OrderItemResponseDTO;
import com.example.eCommerce.entity.Order;
import com.example.eCommerce.entity.OrderItem;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.entity.User;
import com.example.eCommerce.repository.OrderRepo;
import com.example.eCommerce.repository.ProductRepo;
import com.example.eCommerce.repository.UserRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public OrderMapper(UserRepo userRepo, ProductRepo productRepo, OrderRepo orderRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public OrderItemResponseDTO orderItemToDto(OrderItem orderItem) {
        if (orderItem == null) return null;
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

    public OrderItem orderItemDtoToEntity(OrderItemResponseDTO dto) {
        if (dto == null) return null;
        OrderItem orderItem = new OrderItem();
        Product product = productRepo.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found In OrderMapper"));
        Order order = orderRepo.findById(dto.getOrderId()).orElseThrow(() -> new RuntimeException("order not found In OrderMapper"));
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setOrder(order);

        return orderItem;
    }

    public List<OrderItem> orderItemsDtoListToEntityList(List<OrderItemResponseDTO> orderItems) {
        return orderItems.stream().map(this::orderItemDtoToEntity).toList();
    }


    public OrderResponseDTO orderToDto(Order order) {
        if (order == null) return null;

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        if (order.getUser() != null) {
            dto.setUserEmail(order.getUser().getEmail());
        }
        dto.setOrderItemResponseDTOList(orderItemToDtoList(order.getOrderItem()));
        dto.setOrderTotalPrice(order.getTotalPrice());
        return dto;
    }

    public List<OrderResponseDTO> orderToDtoList(List<Order> orders) {
        if (orders == null) return null;
        return orders.stream().map(this::orderToDto).toList();
    }

    public Order orderToEntity(OrderResponseDTO orderResponseDTO) {
        if (orderResponseDTO == null) return null;
        Order order = new Order();
        User user = userRepo.findByEmail(orderResponseDTO.getUserEmail()).orElseThrow(() -> new RuntimeException("User Not Found By Email"));
        List<OrderItem> orderItems = orderItemsDtoListToEntityList(orderResponseDTO.getOrderItemResponseDTOList());
        order.setUser(user);
        order.setOrderItem(orderItems);
        order.setTotalPrice(orderResponseDTO.getOrderTotalPrice());
        return order;
    }


}
