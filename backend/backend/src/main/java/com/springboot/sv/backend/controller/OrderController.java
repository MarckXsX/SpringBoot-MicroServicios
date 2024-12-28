package com.springboot.sv.backend.controller;

import com.springboot.sv.backend.dto.*;
import com.springboot.sv.backend.entities.Order;
import com.springboot.sv.backend.entities.Payment;
import com.springboot.sv.backend.service.OrderService;
import com.springboot.sv.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto order) {
        GenericResponseDto<OrderResponseDto> response = orderService.createOrder(order);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id){
        GenericResponseDto<OrderResponseDto> response = paymentService.processPayment(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
