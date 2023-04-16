package com.example.tuonlineclothingstore.dtos.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long id ;
    private String name;
    private String image;
    private Boolean isDeleted;
}
