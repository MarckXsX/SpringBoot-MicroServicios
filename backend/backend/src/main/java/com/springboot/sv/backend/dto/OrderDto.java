package com.springboot.sv.backend.dto;

import lombok.Data;


import java.util.List;

@Data
public class OrderDto {
    private Long customer;
    private List<OrderDetailDto> produts;
}
