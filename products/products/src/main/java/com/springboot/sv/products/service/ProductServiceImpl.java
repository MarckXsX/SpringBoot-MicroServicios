package com.springboot.sv.products.service;

import com.springboot.sv.products.dto.GenericResponseDto;
import com.springboot.sv.products.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private static final String EXTERNAL_API_URL = "https://fakestoreapi.com/products";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<List<ProductDto>> getAllProducts() {

        try {
            ProductDto[] products = restTemplate.getForObject(EXTERNAL_API_URL, ProductDto[].class);
            List<ProductDto> productDtoList = Arrays.asList(products);

            return new GenericResponseDto<>(
                    productDtoList,
                    "Successfull operation.",
                    200
            );
        }catch (Exception e){
            return new GenericResponseDto<>(
                    null,
                    "Error in operation.",
                    500
            );
        }
    }

    @Override
    public GenericResponseDto<ProductDto> getProductById(Long id) {
        String newUrl = EXTERNAL_API_URL + "/"+ id;

        ProductDto productDto = restTemplate.getForObject(newUrl,ProductDto.class);
        if(productDto == null){
            return new GenericResponseDto<>(
                    null,
                    "Product not found for ID: " + id,
                    404
            );
        }
        return new GenericResponseDto<>(
                productDto,
                "Successfull operation.",
                200);
    }
}
