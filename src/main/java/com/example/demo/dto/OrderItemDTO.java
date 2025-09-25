package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productid;
    private Integer quantity;
    private BigDecimal price;
}
