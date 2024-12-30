package com.springboot.sv.products.service;

import com.springboot.sv.products.ProductsApplication;
import com.springboot.sv.products.dto.GenericResponseDto;
import com.springboot.sv.products.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Value("${external.api.url}")
    private  String externalApiUrl;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<List<ProductDto>> getAllProducts() {

        try {
            ProductDto[] products = restTemplate.getForObject(externalApiUrl, ProductDto[].class);
            List<ProductDto> productDtoList = Arrays.asList(products);

            logger.info("Products successfully recovered: {}", productDtoList);
            return GenericResponseDto.<List<ProductDto>>builder()
                    .data(productDtoList)
                    .message("Successfull operation.")
                    .status(HttpStatus.OK.value())
                    .build();
        }catch (Exception e){
            logger.warn("Products  not found");
            return GenericResponseDto.<List<ProductDto>>builder()
                    .data(null)
                    .message("Error in operation." + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @Override
    public GenericResponseDto<ProductDto> getProductById(Long id) {
        String newUrl = externalApiUrl + "/"+ id;

        ProductDto productDto = new ProductDto();
        try {
            productDto = restTemplate.getForObject(newUrl,ProductDto.class);

            if(productDto == null){
                logger.warn("Product with ID {} not found", id);
                return  GenericResponseDto.<ProductDto>builder()
                        .data(null)
                        .message("Product not found for ID: " + id)
                        .status(HttpStatus.NOT_FOUND.value())
                        .build();
            }
            logger.info("Product successfully recovered: {}", productDto.getTitle());
            return  GenericResponseDto.<ProductDto>builder()
                    .data(productDto)
                    .message("Successfull operation.")
                    .status(HttpStatus.OK.value())
                    .build();
        }catch (Exception e){
            return  GenericResponseDto.<ProductDto>builder()
                    .data(null)
                    .message("Error in operation." + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}
