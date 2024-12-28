package com.springboot.sv.backend.repositories;

import com.springboot.sv.backend.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
