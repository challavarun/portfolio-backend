package com.example.demo.dto;

import com.example.demo.model.AddressModel;
import com.example.demo.model.UserRole;
import lombok.Data;

@Data
public class UserResponce {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
