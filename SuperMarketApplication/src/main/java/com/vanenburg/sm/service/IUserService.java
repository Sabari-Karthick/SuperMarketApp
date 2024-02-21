package com.vanenburg.sm.service;



import java.util.List;

import org.springframework.validation.BindingResult;

import com.vanenburg.sm.domain.LoyaltyCard;
import com.vanenburg.sm.dto.auth.AuthRequest;
import com.vanenburg.sm.dto.auth.AuthResponse;
import com.vanenburg.sm.dto.card.LoyaltyCardResponse;
import com.vanenburg.sm.dto.user.BasicUserResponse;
import com.vanenburg.sm.dto.user.UserRegistrationRequest;
import com.vanenburg.sm.dto.user.UserResponse;

public interface IUserService {
      String registerUser(UserRegistrationRequest request,BindingResult bindingResult);
      UserResponse getUserById(Integer userID);
      String deleteUserById(Integer userID);
      LoyaltyCard addLoyaltyCard(Integer userID);
      LoyaltyCardResponse getLoyaltyCardOfUser(Integer userId);
      List<BasicUserResponse> getAllUsers();
      AuthResponse login(AuthRequest request);
      LoyaltyCardResponse addLoyaltyPoints(Integer userId, Double price);
}
