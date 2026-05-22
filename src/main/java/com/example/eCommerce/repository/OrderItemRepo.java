package com.example.eCommerce.repository;

import com.example.eCommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    Optional<OrderItem> findByOrderIdAndProductId(
            Long orderId,
            Long productId
    );
}
