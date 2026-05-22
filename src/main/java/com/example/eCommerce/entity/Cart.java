package com.example.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy ="cart")
    private List<OrderItem> orderItem;

    //orphanRemoval لم امسحه من الليست يتمسح تلقائى من اى مكان اخر هو موجود فيه
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems;

}