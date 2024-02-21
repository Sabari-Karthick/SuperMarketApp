package com.vanenburg.sm.dto.auth;

import com.vanenburg.sm.dto.user.UserResponse;

import lombok.Data;

@Data
public class AuthResponse {
	private UserResponse user;
	private String token;

}
