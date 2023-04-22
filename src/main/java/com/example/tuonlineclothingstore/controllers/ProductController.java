package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.*;
import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.Product.CreateProductDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.dtos.Product.UpdateProductDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.services.category.ICategoryService;
import com.example.tuonlineclothingstore.services.productimage.IProductImageService;
import com.example.tuonlineclothingstore.services.product.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    IProductService iProductService;
    @Autowired
    ICategoryService iCategoryService;
    @Autowired
    IProductImageService iProductImageService;
//    private final int size = 1;

    private final String column = "price";
    @GetMapping("")
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "") String searchText,
                                                           @RequestParam(defaultValue = "0") long categoryId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "12") int size,
                                                           @RequestParam(defaultValue = "0") int sortType
    ) {
        String sort = "asc";
        if (sortType!=0) sort = "desc";
        return new ResponseEntity<>(iProductService.filter(searchText,categoryId, page, size, sort, column), HttpStatus.OK);
    }
    @GetMapping("/best-selling")
    public ResponseEntity<List<ProductDto>> getTop10BestSelling() {
        List<ProductDto> listProduct = iProductService.getTop8ProductBySold();
        if (listProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }
    @GetMapping("/new")
    public ResponseEntity<List<ProductDto>> getTop10New() {
        List<ProductDto> listProduct = iProductService.getTop8NewProducts();
        if (listProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {
        ProductDto Product = iProductService.getProductById(id);
        return new ResponseEntity<>(Product, HttpStatus.OK);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> geProductByCategory(@PathVariable("id") long id){
        List<ProductDto> listProduct = iProductService.getProductByCategoryId(id);
        if (listProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto productDto) {
        return new ResponseEntity<>(iProductService.createProduct(productDto), HttpStatus.CREATED);
    }


    @PutMapping(value = "/update/{ProductId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("ProductId") Long ProductId,
                                                    @RequestBody UpdateProductDto productDto) {
        return new ResponseEntity<>(iProductService.updateProduct(ProductId, productDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/change-status/{ProductId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@PathVariable("ProductId") Long ProductId) {
        ProductDto productDto = iProductService.getProductById(ProductId);
        iProductService.changeStatus(ProductId);
        return new ResponseEntity<>(String.format("Product đã được thay đổi trạng thái từ %s thành %s",
                productDto.getIsActive(), !productDto.getIsActive()), HttpStatus.OK);
    }
}
