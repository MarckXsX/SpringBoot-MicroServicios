package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.GenericResponseDto;

public interface PaymentService {
    GenericResponseDto<?> processPayment(Long orderId);
}
