package com.springboot.sv.order.controller;

import com.springboot.sv.order.dto.GenericResponseDto;
import com.springboot.sv.order.dto.OrderDto;
import com.springboot.sv.order.dto.OrderResponseDto;
import com.springboot.sv.order.service.OrderService;
import com.springboot.sv.order.service.PaymentService;
import com.springboot.sv.order.service.PaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order-service")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto order, BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        GenericResponseDto<?> response = orderService.createOrder(order);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id){
        GenericResponseDto<?> response = paymentService.processPayment(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field " + err.getField() + ", he following error occurs: " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(GenericResponseDto.<Map<String,String>>builder()
                .data(errors)
                .message("Invalid request data")
                .status(HttpStatus.BAD_REQUEST.value())
                .build()
        );
    }
}
