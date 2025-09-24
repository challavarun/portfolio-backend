package com.example.demo.dto;

import lombok.Data;

@Data
public class CartItemReq {
    private Long productId;
    private Integer quantity;
}
