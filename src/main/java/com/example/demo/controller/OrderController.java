package com.example.demo.controller;

import com.example.demo.dto.OrderResponse;
import com.example.demo.services.OrderSrevice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderSrevice orderSrevice;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader ("X-User-ID") String userId){
        return orderSrevice.createOrder(userId)
                .map(orderResponse -> new ResponseEntity<>(orderResponse,HttpStatus.CREATED)
                        ).orElseGet(()->ResponseEntity.badRequest().build());
    }
}
