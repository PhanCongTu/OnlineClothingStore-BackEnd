package com.example.tuonlineclothingstore.services.productsize;

import com.example.tuonlineclothingstore.dtos.ProductSizeDto;

import java.util.List;

public interface IProductSizeService {
    List<ProductSizeDto> getAllSizesByProductId(Long productId);

    ProductSizeDto addProductSize(ProductSizeDto productSizeDto, Long productId);

    void deleteProductSize(Long pSizeId);
}
