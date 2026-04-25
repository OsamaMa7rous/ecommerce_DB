package com.example.eCommerce.dto.productDto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private String name;
    private double price;
    private String description;
    private int stock;
}
