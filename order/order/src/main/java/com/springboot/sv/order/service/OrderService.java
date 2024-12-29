package com.springboot.sv.order.service;

import com.springboot.sv.order.dto.GenericResponseDto;
import com.springboot.sv.order.dto.OrderDto;
import com.springboot.sv.order.dto.OrderResponseDto;

public interface OrderService {

    GenericResponseDto<?> createOrder(OrderDto order);
}
