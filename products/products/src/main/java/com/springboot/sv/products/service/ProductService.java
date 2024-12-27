package com.springboot.sv.products.service;

import com.springboot.sv.products.dto.GenericResponseDto;
import com.springboot.sv.products.dto.ProductDto;

import java.util.List;

public interface ProductService {

    GenericResponseDto<List<ProductDto>> getAllProducts();
    GenericResponseDto<ProductDto> getProductById(Long id);
}
