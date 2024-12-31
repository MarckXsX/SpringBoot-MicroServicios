package com.springboot.sv.products.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private Double price;
    //private String image;
    private String description;
    //private String category;
}
