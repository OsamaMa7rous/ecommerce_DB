package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.productDto.ProductRequestDTO;
import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }


    public static ProductResponseDTO toDto(Product product) {
        ProductResponseDTO newProduct = new ProductResponseDTO();
        newProduct.setDescription(product.getDescription());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        return newProduct;
    }
//ssssssssssssss
    public static List<ProductResponseDTO> toDtoList(List<Product> products) {
       return products.stream().map(ProductMapper::toDto).toList();

    }

}
