package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.*;
import com.example.tuonlineclothingstore.dtos.ProductDto;
import com.example.tuonlineclothingstore.services.category.ICategoryService;
import com.example.tuonlineclothingstore.services.productimage.IProductImageService;
import com.example.tuonlineclothingstore.services.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Api/Product")
public class ProductController {
    @Autowired
    IProductService iProductService;
    @Autowired
    ICategoryService iCategoryService;
    @Autowired
    IProductImageService iProductImageService;

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> listProduct = iProductService.getAllProducts();
        if (listProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
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
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        CategoryDto categoryDto = iCategoryService.getCategoryById(productDto.getCategory().getId());
        productDto.setCategory(categoryDto);
        List<ProductImageDto> images = new ArrayList<>();
        if(productDto.getProductImages() != null)
        {
            images = productDto.getProductImages();
        }
        productDto.setProductImages(null);
        ProductDto savedProduct = iProductService.createProduct(productDto);

        // Add product image into product has been created
        List<ProductImageDto> newImages = new ArrayList<>();
        for (ProductImageDto image : images
        ) {
            ProductImageDto newOne = iProductImageService.addProductImage(image, savedProduct.getId());
            newImages.add(newOne);
        }
        ProductDto finalProduct = iProductService.getProductById(savedProduct.getId());
        finalProduct.setProductImages(newImages);
        return new ResponseEntity<>(finalProduct, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{ProductId}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable("ProductId") Long ProductId,
                                                   @RequestBody Map<Object, Object> ProductDto) {
        ProductDto updatedProduct = iProductService.patchProduct(ProductId, ProductDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{ProductId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("ProductId") Long ProductId,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = iProductService.updateProduct(ProductId, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{ProductId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("ProductId") Long ProductId) {
        iProductService.deleteProduct(ProductId);
        return new ResponseEntity<>("Product successfully deleted !!", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam("name") String name) {
        List<ProductDto> ProductDtos = iProductService.searchByProductName(name);
        return new ResponseEntity<>(ProductDtos, HttpStatus.OK);
    }
}
