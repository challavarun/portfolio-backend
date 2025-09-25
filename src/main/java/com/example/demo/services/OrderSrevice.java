package com.example.demo.services;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrderResponse;
import com.example.demo.model.*;
        import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class OrderSrevice {
    @Autowired
    CartService cartService;
    @Autowired
    userRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItems> cartItems=cartService.getCart(userId);
        if (cartItems.isEmpty()){
            return Optional.empty();
        }
        Optional<UserModel> userModel = userRepo.findById(Long.valueOf(userId));
        if(userModel.isEmpty()){
            return Optional.empty();
        }
        UserModel user = userModel.get();
        BigDecimal totalprice= cartItems.stream()
                .map(CartItems::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        Order order=new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFORMED);
        order.setTotalAmount(totalprice);
        List<OrderItem> orderItems = cartItems.stream( )
                .map (item -> new OrderItem(
                        null, item. getProduct(),
                        item. getQuantity(),
                        item .getPrice(),
                        order
                )) .toList() ;
        order.setItems(orderItems);
        Order savedOrder=orderRepo.save(order);
        cartService.clearCart(user);
        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList() ,
                savedOrder.getCreatedAt());

    }

}
