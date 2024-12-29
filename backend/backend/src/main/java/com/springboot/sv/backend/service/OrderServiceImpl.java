package com.springboot.sv.backend.service;

import com.springboot.sv.backend.dto.GenericResponseDto;
import com.springboot.sv.backend.dto.OrderDto;
import com.springboot.sv.backend.dto.OrderResponseDto;
import com.springboot.sv.backend.entities.Customer;
import com.springboot.sv.backend.entities.Order;
import com.springboot.sv.backend.entities.OrderDetail;
import com.springboot.sv.backend.repositories.CustomerRepository;
import com.springboot.sv.backend.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public GenericResponseDto<OrderResponseDto> createOrder(OrderDto orderRequest) {


        Customer customer = customerRepository.findById(orderRequest.getCustomer()).orElse(null);
        if(customer == null){
            logger.error("Error: Customer with ID {} not found", orderRequest.getCustomer());
            return GenericResponseDto.<OrderResponseDto>builder()
                    .data(null)
                    .message("Error: Customer not found with ID " + orderRequest.getCustomer())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(new Date());

        logger.info("Mapping product details for the order");
        List<OrderDetail> details = orderRequest.getProduts().stream()
                .map(produts -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setProductId(produts.getProductId());
                    detail.setQuantity(produts.getQuantity());
                    detail.setPrice(produts.getPrice());
                    detail.setOrder(order);
                    return detail;
                }).toList();

        order.setOrderDetails(details);

        Order saverOrder = orderRepository.save(order);
        logger.info("Order created successfully with ID: {}", saverOrder.getId());

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(saverOrder.getId());
        orderResponseDto.setCustomerId(saverOrder.getCustomer().getId());
        orderResponseDto.setTotalAmount(saverOrder.getTotalAmount());
        orderResponseDto.setCreatedAt(saverOrder.getCreatedAt());
        orderResponseDto.setOrderDetails(saverOrder.getOrderDetails());

        return GenericResponseDto.<OrderResponseDto>builder()
                .data(orderResponseDto)
                .message("Order created successfully ")
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
