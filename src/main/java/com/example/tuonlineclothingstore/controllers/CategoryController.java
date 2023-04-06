package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CategoryDto;
import com.example.tuonlineclothingstore.services.category.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/Category")
public class CategoryController {
    @Autowired
    ICategoryService iCategoryService;


    @GetMapping("")
    @ApiOperation(value = "Lấy tất cả category")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> listCategory = iCategoryService.getAllCategories();
        if (listCategory.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }
//    @GetMapping("/pagination")
//    public ResponseEntity<List<CategoryDto>> getAllCategoriesPagination() {
//        List<CategoryDto> listCategory = iCategoryService.getAllPagingCategories();
//        if (listCategory.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(listCategory, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "lấy category theo id")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") long id) {
        CategoryDto category = iCategoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @ApiOperation(value = "Tạo mới category")
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = iCategoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

//    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CategoryDto> patchCategory(@PathVariable("id") Long categoryId,
//                                                     @RequestBody Map<Object, Object> categoryDto) {
//        CategoryDto updatedCategory = iCategoryService.patchCategory(categoryId , categoryDto);
//        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
//    }

    @ApiOperation(value = "Cập nhật lại category")
    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long categoryId,
                                                     @RequestBody CategoryDto categoryDto) throws NoSuchFieldException, IllegalAccessException {
        CategoryDto updatedCategory = iCategoryService.updateCategory(categoryId , categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @ApiOperation(value = "Xóa category theo id")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        iCategoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category successfully deleted !!", HttpStatus.OK);
    }

    @ApiOperation(value = "Tìm kiếm category theo tên")
    @GetMapping("/search/{name}")
    public ResponseEntity<List<CategoryDto>> searchByName(@PathVariable("name") String name){
        List<CategoryDto> categoryDtos = iCategoryService.searchByName(name);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
}













