package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.ProductMapper;
import com.example.eCommerce.dto.productDto.ProductRequestDTO;
import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.entity.Category;
import com.example.eCommerce.entity.Product;
import com.example.eCommerce.repository.CategoryRepo;
import com.example.eCommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;

    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepo.findById(productRequestDTO.getCategoryId()).get();
        Product product = ProductMapper.toEntity(productRequestDTO);
        product.setCategory(category);
        Product save = productRepo.save(product);
        return productMapper.toDto(save);

    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product oldProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

        oldProduct.setName(productRequestDTO.getName());
        oldProduct.setPrice(productRequestDTO.getPrice());
        oldProduct.setStock(productRequestDTO.getStock());
        oldProduct.setDescription(productRequestDTO.getDescription());
        Product updatedProduct = productRepo.save(oldProduct);
        return productMapper.toDto(updatedProduct);

    }

    public String deleteProduct(Long id) {
        Product oldProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        productRepo.delete(oldProduct);
        return "success";
    }

    public ProductResponseDTO getProductById(Long id) {
        Product byId = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        return productMapper.toDto(byId);

    }

    public List<ProductResponseDTO> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return productMapper.toDtoList(products);

    }


}
