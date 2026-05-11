package com.example.eCommerce.service;

import com.example.eCommerce.Mapper.CategoryMapper;
import com.example.eCommerce.dto.CategoryDto.CategoryResponseDTO;
import com.example.eCommerce.entity.Category;
import com.example.eCommerce.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo repository;
    private final CategoryMapper mapper;

    public CategoryResponseDTO addCategory(Category category) {
        Category save = repository.save(category);
        return mapper.toDto(save);
    }

    public CategoryResponseDTO updateCategory(Long id, Category category) {
        Category update = repository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("category is null"));
        update.setName(category.getName());
        update.setId(category.getId());
        Category updated = repository.save(update);
        return mapper.toDto(updated);

    }

    public String deleteCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("category is null"));
        repository.delete(category);
        return "Category Deleted successfully";

    }

    public CategoryResponseDTO getOneCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("category is null"));
        return mapper.toDto(category);
    }

    public List<CategoryResponseDTO> getCategories() {
        List<Category> categories = repository.findAll();
        return mapper.toDtoList(categories);
    }

}
