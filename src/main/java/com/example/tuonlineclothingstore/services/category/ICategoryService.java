package com.example.tuonlineclothingstore.services.category;

import com.example.tuonlineclothingstore.dtos.CategoryDto;
import com.example.tuonlineclothingstore.dtos.CategoryPagination;

import java.util.List;
import java.util.Map;

public interface ICategoryService {

    //    public CategoryServiceImpl(CategoryRepository categoryRepository){
//        this.categoryRepository = categoryRepository;
//    }
    List<CategoryDto> getAllCategories();

    CategoryPagination getAllPagingCategories(int pageNo, int pageSize, String sortBy, String sortDir);

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto patchCategory(Long id, Map<Object, Object> categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws NoSuchFieldException, IllegalAccessException;

    void deleteCategory(Long categoryId);

    List<CategoryDto> searchByName(String name);
}
