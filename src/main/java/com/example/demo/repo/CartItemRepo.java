package com.example.demo.repo;

import com.example.demo.model.CartItems;
import com.example.demo.model.Product;
import com.example.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CartItemRepo extends JpaRepository<CartItems,Long> {


    CartItems findByUserAndProduct(UserModel user, Product product);

    void deleteByUserAndProduct(UserModel user1, Product product1);

    List <CartItems> findByUser(UserModel user);

    void deleteByUser(UserModel userId);
}
