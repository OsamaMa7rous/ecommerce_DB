package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.productDto.ProductRequestDTO;
import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }


    public ProductResponseDTO toDto(Product product) {
        if (product == null) return null;

        ProductResponseDTO newProduct = new ProductResponseDTO();
        newProduct.setId(product.getId());
        newProduct.setCategoryId(product.getCategory().getId());
        newProduct.setDescription(product.getDescription());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        return newProduct;
    }

    public List<ProductResponseDTO> toDtoList(List<Product> products) {
        if (products == null) return null;

        return products.stream().map(this::toDto).toList();

    }

}
