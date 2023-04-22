package com.example.tuonlineclothingstore.dtos.Product;

import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.ProductImageDto;
import com.example.tuonlineclothingstore.dtos.ProductSizeDto;
import com.example.tuonlineclothingstore.entities.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String productName;
    private int quantity;
    private int sold;
    private String description;
    private int price;
    private Boolean isActive = true;
    private CategoryDto category;
    private List<ProductImageDto> productImages;
    private List<ProductSizeDto> productSizes;
}
