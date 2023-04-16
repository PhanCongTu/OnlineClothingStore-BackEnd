package com.example.tuonlineclothingstore.dtos;

import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private long id;

    private int quantity;

    private ProductDto product;
}
