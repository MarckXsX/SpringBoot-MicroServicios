package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.GenericResponseDto;
import com.springboot.sv.order.dto.OrderResponseDto;
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

@Service
public class PaymentServiceImpl implements PaymentService{

    private  static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${external.api.payment}")
    private String externarUrlPayment;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GenericResponseDto<?> processPayment(Long orderId) {
        try {
            logger.info("consuming banckend-payOrder");
            ResponseEntity<GenericResponseDto<OrderResponseDto>> response = restTemplate.exchange(
                    externarUrlPayment + "/" + orderId + "/pay",
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<GenericResponseDto<OrderResponseDto>>() {}
            );
            return response.getBody();
        }catch (HttpClientErrorException e){
            logger.error("payment processing error: {}", e.getResponseBodyAsString());
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("payment processing error: " + e.getResponseBodyAsString())
                    .status(e.getStatusCode().value())
                    .build();
        }
    }
}
