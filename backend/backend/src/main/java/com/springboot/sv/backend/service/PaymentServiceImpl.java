package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderResponseDto;
import com.springboot.sv.backend.entities.Order;
import com.springboot.sv.backend.entities.Payment;
import com.springboot.sv.backend.repositories.OrderRepository;
import com.springboot.sv.backend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public GenericResponseDto<OrderResponseDto> processPayment(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.orElseThrow();
            if(!orderRepository.existsByIdAndPayment_Status(order.getId(),"APROVED")){
                double totalAmount = order.getOrderDetails().stream()
                        .mapToDouble(produts -> produts.getPrice()*produts.getQuantity())
                        .sum();
                order.setTotalAmount(totalAmount);

                Payment payment = new Payment();
                payment.setOrder(order);
                payment.setStatus(Math.random() > 0.4 ? "APROVED" : "REJECTED");
                paymentRepository.save(payment);

                OrderResponseDto orderResponseDto = new OrderResponseDto();
                orderResponseDto.setId(order.getId());
                orderResponseDto.setCustomerId(order.getCustomer().getId());
                orderResponseDto.setTotalAmount(order.getTotalAmount());
                orderResponseDto.setCreatedAt(order.getCreatedAt());
                orderResponseDto.setOrderDetails(order.getOrderDetails());
                return GenericResponseDto.<OrderResponseDto>builder()
                        .data(orderResponseDto)
                        .message("successful payment")
                        .status(HttpStatus.OK.value())
                        .build();

            }
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("payment already processed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        return GenericResponseDto.<OrderResponseDto>builder()
                .data(null)
                .message("payment not found")
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
