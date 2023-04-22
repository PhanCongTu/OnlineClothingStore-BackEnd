package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.ProductImageDto;
import com.example.tuonlineclothingstore.services.productimage.IProductImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-image")
@CrossOrigin("http://localhost:3000")
public class ProductImageController {
    @Autowired
    IProductImageService iProductImageService;

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Lấy tất cả ảnh Product theo Product Id")
    public ResponseEntity<List<ProductImageDto>> getAllProductImages(@PathVariable Long productId) {
        List<ProductImageDto> listImages = iProductImageService.getAllImageByProductId(productId);
        if (listImages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listImages, HttpStatus.OK);
    }

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductImageDto> addImage(@RequestBody ProductImageDto productImageDto,
                                                    @PathVariable("productId") Long productId){
        ProductImageDto newImage = iProductImageService.addProductImage(productImageDto, productId);
        return new ResponseEntity<>(newImage, HttpStatus.OK);
    }

    @ApiOperation(value = "Xóa product image bằng id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long imageId){
        iProductImageService.deleteProductImage(imageId);
        return new ResponseEntity<>("Product Image deleted successfully !!", HttpStatus.OK);
    }
}
