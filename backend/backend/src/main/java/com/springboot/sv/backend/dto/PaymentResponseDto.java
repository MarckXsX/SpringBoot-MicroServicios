package com.springboot.sv.backend.dto;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long orderId;
    private String status;
}
