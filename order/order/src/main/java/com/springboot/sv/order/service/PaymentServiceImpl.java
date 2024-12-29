package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.GenericResponseDto;
import com.springboot.sv.order.dto.OrderResponseDto;
import com.springboot.sv.order.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Value("${external.api.payment}")
    private String externarUrlPayment;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<?> processPayment(Long orderId) {
        try {
            ResponseEntity<GenericResponseDto<OrderResponseDto>> response = restTemplate.exchange(
                    externarUrlPayment + "/" + orderId + "/pay",
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<GenericResponseDto<OrderResponseDto>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e){
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("Error processing order: " + e.getResponseBodyAsString())
                    .status(e.getStatusCode().value())
                    .build();
        }
    }
}
