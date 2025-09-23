package com.example.demo.services;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserReq;
import com.example.demo.dto.UserResponce;
import com.example.demo.model.AddressModel;
import com.example.demo.model.UserModel;
import com.example.demo.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    userRepo userRepo;
    public void addUser(UserReq userReq) {
        UserModel user=new UserModel();
        updateUserFromRequest(user,userReq);
        userRepo.save(user);
    }

    private void updateUserFromRequest(UserModel user, UserReq userReq) {
        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setEmail(userReq.getEmail());
        user.setPhone(userReq.getPhone());
        if (userReq.getAddress() != null) {
            AddressModel address = new AddressModel();
            address.setStreet(userReq.getAddress().getStreet());
            address.setCity(userReq.getAddress().getCity());
            address.setState(userReq.getAddress().getState());
            address.setCountry(userReq.getAddress().getCountry());
            address.setZipcode(userReq.getAddress().getZipcode());

            user.setAddress(address); // ✅ VERY IMPORTANT (attach entity to user)
        }
    }

    public List<UserResponce> fetchAllUsers() {
        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    private UserResponce mapToUserResponse(UserModel user){
        UserResponce response = new UserResponce();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;

    }

    public Optional<UserResponce> fetchUser(Long id) {
        return userRepo.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserReq updatedUserRequest) {
        return userRepo.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepo.save(existingUser);
                    return true;
                }).orElse(false);
    }
}
