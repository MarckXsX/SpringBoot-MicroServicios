package com.springboot.sv.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseDto<T> {
    private T data;
    private String message;
    private Integer status;
}
