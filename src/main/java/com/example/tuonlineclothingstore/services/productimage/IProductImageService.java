package com.example.tuonlineclothingstore.services.productimage;

import com.example.tuonlineclothingstore.dtos.ProductImageDto;

import java.util.List;

public interface IProductImageService {
    List<ProductImageDto> getAllImageByProductId(Long productId);

    ProductImageDto addProductImage(ProductImageDto productImageDto, Long productId);

    void deleteProductImage(Long pImage);
}
