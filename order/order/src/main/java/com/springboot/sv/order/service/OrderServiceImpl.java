package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private  static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${external.api.order}")
    private String externarUrlOrder;

    @Value("${external.api.product}")
    private String externarUrlProduct;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<?> createOrder(OrderDto orderRequest) {

        List<OrderDetailDto> validProducts = new ArrayList<>();
        List<Long> invalidProductIds = new ArrayList<>();

        for (OrderDetailDto product : orderRequest.getProduts()) {
            try {
                logger.info("consuming product-service");
                ResponseEntity<GenericResponseDto<ProductDto>> response = restTemplate.exchange(
                        externarUrlProduct + "/" + product.getProductId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<GenericResponseDto<ProductDto>>() {}
                );

                GenericResponseDto<ProductDto> productResponse = response.getBody();

                logger.info("Products successfully recovered: {}", productResponse.getData());

                ProductDto productData = productResponse.getData();

                OrderDetailDto validProduct = new OrderDetailDto();
                validProduct.setProductId(product.getProductId());
                validProduct.setQuantity(product.getQuantity());
                validProduct.setPrice(productData.getPrice());
                validProducts.add(validProduct);

            } catch (Exception e) {
                invalidProductIds.add(product.getProductId());
                logger.error("error get product: {}", e.getMessage());
            }
        }

        if (!invalidProductIds.isEmpty()) {
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("Error in the products: " + invalidProductIds)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(orderRequest.getCustomer());
        orderDto.setProduts(validProducts);
        logger.info("creation of the orderDto: {}", orderDto);

        try {
            logger.info("consuming banckend-createOrder");
            ResponseEntity<GenericResponseDto<OrderResponseDto>> response = restTemplate.exchange(
                    externarUrlOrder,
                    HttpMethod.POST,
                    new HttpEntity<>(orderDto),
                    new ParameterizedTypeReference<GenericResponseDto<OrderResponseDto>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e){
            logger.error("Error creating order: {}", e.getResponseBodyAsString());
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("Error creating order: " + e.getResponseBodyAsString())
                    .status(e.getStatusCode().value())
                    .build();
        }
    }
}
