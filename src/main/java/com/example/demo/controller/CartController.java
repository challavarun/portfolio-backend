package com.example.demo.controller;

import com.example.demo.dto.CartItemReq;
import com.example.demo.model.CartItems;
import com.example.demo.services.CartService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping
    public ResponseEntity<String> creatACart(
            @RequestHeader("X-user_id") String userId,
            @RequestBody CartItemReq request){
        if (!cartService.addToCart(userId,request)){
            return ResponseEntity.badRequest().body("product out of stock or usernot found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> dremoveFromCart(
            @RequestHeader("X-user_id") String userId,
            @PathVariable Long id
    ){
       boolean deleted= cartService.deleteFrromcart(userId,id);
       return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();


    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<List<CartItems>> getcrt(
            @RequestHeader("X-user_id") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }

}
