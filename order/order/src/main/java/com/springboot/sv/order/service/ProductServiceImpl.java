package com.springboot.sv.order.service;

import com.springboot.sv.order.controller.ProductController;
import com.springboot.sv.order.dto.GenericResponseDto;
import com.springboot.sv.order.dto.OrderResponseDto;
import com.springboot.sv.order.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private  static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${external.api.product}")
    private String externarUrlProduct;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<?> getAllProducts() {
        try {
            logger.info("consuming product-service-getAllProducts");
            ResponseEntity<GenericResponseDto<?>> response = restTemplate.exchange(
                    externarUrlProduct,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GenericResponseDto<?>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e){
            logger.error("Error in operation: {}", e.getResponseBodyAsString());
            return GenericResponseDto.<ProductDto>builder()
                    .data(null)
                    .message("Error in operation: " + e.getResponseBodyAsString())
                    .status(e.getStatusCode().value())
                    .build();
        }
    }

    @Override
    public GenericResponseDto<?> getProductById(Long id) {
        try {
            logger.info("consuming product-service-getProductById");
            ResponseEntity<GenericResponseDto<ProductDto>> response = restTemplate.exchange(
                    externarUrlProduct + "/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GenericResponseDto<ProductDto>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e){
            logger.error("Error in operation: {}", e.getResponseBodyAsString());
            return GenericResponseDto.<ProductDto>builder()
                    .data(null)
                    .message("Error in operation: " + e.getResponseBodyAsString())
                    .status(e.getStatusCode().value())
                    .build();
        }
    }
}
