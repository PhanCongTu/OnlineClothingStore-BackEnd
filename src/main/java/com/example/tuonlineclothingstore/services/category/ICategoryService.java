package com.example.tuonlineclothingstore.services.category;

import com.example.tuonlineclothingstore.dtos.Category.CreateCategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.UpdateCategoryDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ICategoryService {

    Page<CategoryDto> filter(String search, int page, int size,
                         String sort, String column);

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto createCategory(CreateCategoryDto categoryDto);

    CategoryDto patchCategory(Long id, Map<Object, Object> categoryDto);

    CategoryDto updateCategory(Long id, UpdateCategoryDto categoryDto);

    void changeStatus(Long categoryId);
}
