package com.vanenburg.sm.dto.user;

import java.util.Set;

import com.vanenburg.sm.enums.Role;

import lombok.Data;

@Data
public class BasicUserResponse {
	private Integer id;

	private String email;
	
	private boolean isLoyaltyRegistered;
	
	private Set<Role> roles;
}
