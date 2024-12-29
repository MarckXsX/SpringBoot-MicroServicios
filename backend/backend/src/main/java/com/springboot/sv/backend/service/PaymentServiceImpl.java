package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderResponseDto;
import com.springboot.sv.backend.dto.PaymentResponseDto;
import com.springboot.sv.backend.entities.Order;
import com.springboot.sv.backend.entities.Payment;
import com.springboot.sv.backend.repositories.OrderRepository;
import com.springboot.sv.backend.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public GenericResponseDto<OrderResponseDto> processPayment(Long orderId) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        logger.info("Order retrieved from backend: {}", optionalOrder);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.orElseThrow();
            if(!orderRepository.existsByIdAndPayment_Status(order.getId(),"APROVED")){
                double totalAmount = order.getOrderDetails().stream()
                        .mapToDouble(produts -> produts.getPrice()*produts.getQuantity())
                        .sum();
                order.setTotalAmount(totalAmount);

                Payment payment = new Payment();
                payment.setOrder(order);
                payment.setStatus("APROVED");
                Payment response = paymentRepository.save(payment);
                logger.info("successful payment: {}", response);

                OrderResponseDto orderResponseDto = new OrderResponseDto();
                orderResponseDto.setId(order.getId());
                orderResponseDto.setCustomerId(order.getCustomer().getId());
                orderResponseDto.setTotalAmount(order.getTotalAmount());
                orderResponseDto.setCreatedAt(order.getCreatedAt());
                orderResponseDto.setOrderDetails(order.getOrderDetails());
                logger.info("Building OrderResponseDto: {}", orderResponseDto);
                return GenericResponseDto.<OrderResponseDto>builder()
                        .data(orderResponseDto)
                        .message("successful payment")
                        .status(HttpStatus.OK.value())
                        .build();

            }
            logger.error("payment already processed");
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("payment already processed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
        logger.error("Order not found");
        return GenericResponseDto.<OrderResponseDto>builder()
                .data(null)
                .message("Order not found")
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
