package com.springboot.sv.backend.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Long productId;
    private Integer quantity;
    private Double price;
}
