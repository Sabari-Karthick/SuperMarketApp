package com.vanenburg.sm.dto.user;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponse extends BasicUserResponse{
	private String password;

	private String firstName;

	private String lastName;
	
	private String city;

	private String phoneNumber;

}
