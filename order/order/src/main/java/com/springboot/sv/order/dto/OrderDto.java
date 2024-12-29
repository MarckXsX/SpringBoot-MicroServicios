package com.springboot.sv.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    @NotNull
    private Long customer;

    @NotEmpty
    @Valid
    private List<OrderDetailDto> produts;
}
