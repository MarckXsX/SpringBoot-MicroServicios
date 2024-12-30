package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderDto;
import com.springboot.sv.backend.dto.OrderResponseDto;

public interface OrderService {

    GenericResponseDto<OrderResponseDto> createOrder(OrderDto order);

}
