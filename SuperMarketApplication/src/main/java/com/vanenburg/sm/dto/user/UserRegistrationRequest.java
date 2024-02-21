package com.vanenburg.sm.dto.user;

import static com.vanenburg.sm.constants.ErrorMessage.*;

import java.util.Set;

import com.vanenburg.sm.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
	@NotBlank(message = EMPTY_FIRST_NAME)
	private String firstName;

	@NotBlank(message = EMPTY_LAST_NAME)
	private String lastName;

	@Size(min = 4, max = 16, message = PASSWORD_CHARACTER_LENGTH)
	private String password;
	
	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	private String city;
    
	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	@Size(min = 10,max = 10)
	private String phoneNumber;


	@Email(message = INCORRECT_EMAIL)
	@NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
	private String email;

	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	private Set<Role> roles;
}
