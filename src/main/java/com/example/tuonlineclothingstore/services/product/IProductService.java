package com.example.tuonlineclothingstore.services.product;

import com.example.tuonlineclothingstore.dtos.Product.CreateProductDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.dtos.Product.UpdateProductDto;
import com.example.tuonlineclothingstore.dtos.ProductPagination;
import com.example.tuonlineclothingstore.entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IProductService {
    Page<ProductDto> filter(String searchText, long categoryId, int page, int size,
                            String sort, String column);

    ProductDto getProductById(Long ProductId);

    ProductDto createProduct(CreateProductDto ProductDto);

    // Cập nhật lại Product (Cập nhật lại toàn bộ các thuộc tính)
    ProductDto updateProduct(Long id, UpdateProductDto productDto);

    void changeStatus(Long ProductId);

    List<ProductDto> getTop8ProductBySold();
    List<ProductDto> getProductByCategoryId(long id);

    List<ProductDto> getTop8NewProducts();
}
