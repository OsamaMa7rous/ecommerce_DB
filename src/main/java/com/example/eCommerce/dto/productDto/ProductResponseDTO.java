package com.example.eCommerce.dto.productDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int stock;
}
