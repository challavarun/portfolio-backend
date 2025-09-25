package com.example.demo.services;

import com.example.demo.dto.CartItemReq;
import com.example.demo.model.CartItems;
import com.example.demo.model.Product;
import com.example.demo.model.UserModel;
import com.example.demo.repo.CartItemRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.userRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    userRepo userRepo;
    @Autowired
    CartItemRepo cartItemRepo;

    public boolean addToCart(String userId, CartItemReq request) {
        Optional<Product> product = productRepo.findById(request.getProductId());
        if (product.isEmpty())
            return false;
        Product product1 = product.get();
        if (product1.getStockQuantity() < request.getQuantity())
            return false;
        Optional<UserModel> user = userRepo.findById(Long.valueOf(userId));
        if (user.isEmpty())
            return false;
        UserModel user1 = user.get();
        CartItems existingcartItem = cartItemRepo.findByUserAndProduct(user1,product1);
        if (existingcartItem != null) {
            existingcartItem.setQuantity(existingcartItem.getQuantity() + request.getQuantity());
            existingcartItem.setPrice(existingcartItem.getPrice().multiply(BigDecimal.valueOf(existingcartItem.getQuantity())));
            cartItemRepo.save(existingcartItem);
        }
        else{
            CartItems cartItems= new CartItems();
            cartItems.setUser(user1);
            cartItems.setProduct(product1);
            cartItems.setQuantity(request.getQuantity());
            cartItems.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepo.save(cartItems);
        }
return true;
    }

    public boolean deleteFrromcart(String userId, Long productId) {
        Optional<Product> product = productRepo.findById(productId);
        Optional<UserModel> user = userRepo.findById(Long.valueOf(userId));
        if (product.isPresent() && user.isPresent()){
            cartItemRepo.deleteByUserAndProduct(user.get(),product.get());
        return true;}

        return false;
    }

    public List<CartItems> getCart(String userId) {
        return userRepo.findById(Long.valueOf(userId))
                .map(cartItemRepo::findByUser)
                .orElseGet(List::of);

    }

    public void clearCart(UserModel userId) {
        cartItemRepo.deleteByUser(userId);

    }
}
