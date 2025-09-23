package com.example.demo.dto;

import lombok.Data;

@Data
public class UserReq {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDTO address;
}
