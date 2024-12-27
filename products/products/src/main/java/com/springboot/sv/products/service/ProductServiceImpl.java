package com.springboot.sv.products.service;

import com.springboot.sv.products.ProductsApplication;
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

            return GenericResponseDto.<List<ProductDto>>builder()
                    .data(productDtoList)
                    .message("Successfull operation.")
                    .status(200)
                    .build();
        }catch (Exception e){
            return GenericResponseDto.<List<ProductDto>>builder()
                    .data(null)
                    .message("Error in operation.")
                    .status(500)
                    .build();
        }
    }

    @Override
    public GenericResponseDto<ProductDto> getProductById(Long id) {
        String newUrl = EXTERNAL_API_URL + "/"+ id;

        ProductDto productDto = restTemplate.getForObject(newUrl,ProductDto.class);

        if(productDto == null){
            return  GenericResponseDto.<ProductDto>builder()
                    .data(null)
                    .message("Product not found for ID: " + id)
                    .status(404)
                    .build();
        }
        return  GenericResponseDto.<ProductDto>builder()
                .data(productDto)
                .message("Successfull operation.")
                .status(200)
                .build();
    }
}
