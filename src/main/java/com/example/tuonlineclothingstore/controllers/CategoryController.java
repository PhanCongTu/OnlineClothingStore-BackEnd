package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.Category.CreateCategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.Category.UpdateCategoryDto;
import com.example.tuonlineclothingstore.services.category.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    ICategoryService iCategoryService;


    /***
     *
     * @param searchText: từ khóa muốn tìm kiếm (name)
     * @param page: Số thứ tự của trang
     * @param column: Field muốn sắp xếp theo
     * @param size: Số lượng kết quả của 1 trang
     * @param sortType: sắp xếp theo:
     *                true => tăng dần,
     *                false => giảm dần
     * @return: Trả về 1 page các category dựa trên các thông tin đầu vào
     */
    @GetMapping("")
    public ResponseEntity<Page<CategoryDto>> getAllCategory(@RequestParam(defaultValue = "") String searchText,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "name") String column,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "true") boolean sortType) {
        String sort = (sortType ? "asc" : "desc") ;
        return new ResponseEntity<>(iCategoryService.filter(searchText, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "lấy category theo id")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") long id) {
        return new ResponseEntity<>(iCategoryService.getCategoryById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Tạo mới category")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return new ResponseEntity<>(iCategoryService.createCategory(createCategoryDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cập nhật lại category")
    @PutMapping(value = "/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody UpdateCategoryDto updateCategoryDto) {
        CategoryDto updatedCategory = iCategoryService.updateCategory(categoryId, updateCategoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @ApiOperation(value = "Đổi trạng thái category")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/change-status/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeStatus(@PathVariable Long categoryId) {

        CategoryDto categoryDto = iCategoryService.getCategoryById(categoryId);
        iCategoryService.changeStatus(categoryId);
        return new ResponseEntity<>(String.format("Category đã được thay đổi trạng thái từ %s thành %s",
                categoryDto.getIsDeleted(), !categoryDto.getIsDeleted()), HttpStatus.OK);
    }
}













