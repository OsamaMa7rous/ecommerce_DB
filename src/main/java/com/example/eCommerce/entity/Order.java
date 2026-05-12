package com.example.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "totalPrice", nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany
    @JoinColumn(name = "orderItemsId")
    private List<OrderItem> orderItem;


}