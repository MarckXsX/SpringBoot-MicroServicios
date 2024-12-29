package com.springboot.sv.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDetailDto {

    @NotNull
    private Long productId;

    @Positive
    @NotNull
    private Integer quantity;

    private Double price;
}
