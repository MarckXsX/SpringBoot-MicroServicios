package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.GenericResponseDto;

import java.util.List;

public interface ProductService {
    GenericResponseDto<?> getAllProducts();
    GenericResponseDto<?> getProductById(Long id);
}
