package com.example.tuonlineclothingstore.dtos;


import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private long id;

    private int quantity;

    private String size;

    private ProductDto product;
}
