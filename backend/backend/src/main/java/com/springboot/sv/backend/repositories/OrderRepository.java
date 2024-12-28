package com.springboot.sv.backend.repositories;

import com.springboot.sv.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {
    boolean existsByIdAndPayment_Status(Long orderId, String status);
}
