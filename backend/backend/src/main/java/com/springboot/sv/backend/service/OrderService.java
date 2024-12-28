package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderDto;
import com.springboot.sv.backend.dto.OrderResponseDto;
import com.springboot.sv.backend.entities.Order;

import java.util.List;

public interface OrderService {

    GenericResponseDto<OrderResponseDto> createOrder(OrderDto order);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
}
