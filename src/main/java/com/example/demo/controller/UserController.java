package com.example.demo.controller;

import com.example.demo.dto.UserReq;
import com.example.demo.dto.UserResponce;
import com.example.demo.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@NoArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<String> createTable(@RequestBody UserReq userReq){
        userService.addUser(userReq);
        return ResponseEntity.ok("User added sucessfully");
    }
    @GetMapping
    public ResponseEntity<List<UserResponce>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponce> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UserReq updateUserRequest){
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }

}
