package com.springboot.sv.backend.controller;

import com.springboot.sv.backend.dto.*;
import com.springboot.sv.backend.entities.Order;
import com.springboot.sv.backend.entities.Payment;
import com.springboot.sv.backend.service.OrderService;
import com.springboot.sv.backend.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private  static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto order) {
        logger.info("Accessing the method createOrder");
        GenericResponseDto<OrderResponseDto> response = orderService.createOrder(order);
        logger.info("Information {}", response.getData());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id){
        logger.info("Accessing the method processPayment");
        GenericResponseDto<OrderResponseDto> response = paymentService.processPayment(id);
        logger.info("Information {}", response.getData());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
