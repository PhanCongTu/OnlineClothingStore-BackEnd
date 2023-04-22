package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.ProductSizeDto;
import com.example.tuonlineclothingstore.services.productsize.IProductSizeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-size")
@CrossOrigin("http://localhost:3000")
public class ProductSizeController {
    @Autowired
    IProductSizeService iProductSizeService;
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Lấy tất cả size Product theo Product Id")
    public ResponseEntity<List<ProductSizeDto>> getAllProductSizes(@PathVariable Long productId) {
        List<ProductSizeDto> listSizes = iProductSizeService.getAllSizesByProductId(productId);
        if (listSizes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listSizes, HttpStatus.OK);
    }
    @PostMapping("/add/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductSizeDto> addSize(@RequestBody ProductSizeDto productSizeDto,
                                                    @PathVariable("productId") Long productId){
        ProductSizeDto newSize = iProductSizeService.addProductSize(productSizeDto, productId);
        return new ResponseEntity<>(newSize, HttpStatus.OK);
    }
    @ApiOperation(value = "Xóa product size bằng id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{sizeId}")
    public ResponseEntity<String> deleteProductSize(@PathVariable Long sizeId){
        iProductSizeService.deleteProductSize(sizeId);
        return new ResponseEntity<>("Product Size deleted successfully !!", HttpStatus.OK);
    }
}
