package com.example.tuonlineclothingstore.dtos.Product;

import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {

    private String productName;

    private int quantity;

    private String description;

    private int price;

    private CategoryDto category;
//
//    private List<ProductImageDto> productImages;
}
