package com.springboot.sv.order.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private Double totalAmount;
    private Date createdAt;
    private List<OrderDetailDto> orderDetails;
}
