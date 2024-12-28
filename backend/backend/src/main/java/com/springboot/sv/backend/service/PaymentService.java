package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderResponseDto;
import com.springboot.sv.backend.entities.Payment;

public interface PaymentService {
    GenericResponseDto<OrderResponseDto> processPayment(Long orderId);
}
