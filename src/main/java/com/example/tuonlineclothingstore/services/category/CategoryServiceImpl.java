package com.example.tuonlineclothingstore.services.category;

import com.example.tuonlineclothingstore.dtos.Category.CreateCategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.UpdateCategoryDto;
import com.example.tuonlineclothingstore.entities.Category;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.CategoryRepository;
import com.example.tuonlineclothingstore.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    //    public CategoryServiceImpl(CategoryRepository categoryRepository){
//        this.categoryRepository = categoryRepository;
//    }

    @Override
    public Page<CategoryDto> filter(String search, int page, int size,
                                String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        return categories.map(category -> modelMapper.map(category, CategoryDto.class));
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> categoryOp = categoryRepository.findById(categoryId);
        if (!categoryOp.isPresent())
            throw new NotFoundException("Cant find category!");
        return modelMapper.map(categoryOp.get(), CategoryDto.class);
    }

    @Override
    public CategoryDto createCategory(CreateCategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        if (category.getIsDeleted() == null) category.setIsDeleted(false);
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }


    // Cập nhật lại category (chỉ cập nhật những thuộc tính muốn thay đổi)
    @Override
    public CategoryDto patchCategory(Long id, Map<Object, Object> categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (!existingCategory.isPresent()) throw new NotFoundException("Unable to update category!");

        categoryDto.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Category.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingCategory.get(), (Object) value);
        });
        existingCategory.get().setUpdateAt(new Date(new java.util.Date().getTime()));
        Category updatedCategory = categoryRepository.save(existingCategory.get());
        CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory, CategoryDto.class);

        return updatedCategoryDto;

    }

    // Cập nhật lại category (cập nhật lại toàn bộ các thuộc tính)
    @Override
    public CategoryDto updateCategory(Long id, UpdateCategoryDto updateCategoryDto) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) throw new NotFoundException("Unable to update category!");

        modelMapper.map(updateCategoryDto, existingCategory);
        existingCategory.setUpdateAt(new Date(new java.util.Date().getTime()));

        return modelMapper.map(categoryRepository.save(existingCategory), CategoryDto.class);

    }

    // Hàm deleteCategory chỉ delete bằng cách set thuộc tính IsDeleted = true chứ không xoá hẳn trong database
    @Override
    public void changeStatus(Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (!existingCategory.isPresent()) throw new NotFoundException("Unable to dalete category!");

        existingCategory.get().setIsDeleted(!existingCategory.get().getIsDeleted());
        existingCategory.get().setUpdateAt(new Date(new java.util.Date().getTime()));
        categoryRepository.save(existingCategory.get());
    }
}
