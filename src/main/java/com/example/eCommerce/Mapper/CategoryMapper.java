package com.example.eCommerce.Mapper;

import com.example.eCommerce.dto.CategoryDto.CategoryResponseDTO;
import com.example.eCommerce.dto.productDto.ProductResponseDTO;
import com.example.eCommerce.entity.Category;
import com.example.eCommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toDto(Category category) {
        if (category == null) throw new IllegalArgumentException("category is null");
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getName());
        return dto;
    }

    public List<CategoryResponseDTO> toDtoList(List<Category> categories) {
        if (categories == null) throw new IllegalArgumentException("categories is null");
       return categories.stream().map(this::toDto).toList();
    }
}
