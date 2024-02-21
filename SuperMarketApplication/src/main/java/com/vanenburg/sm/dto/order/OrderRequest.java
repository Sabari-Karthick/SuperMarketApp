package com.vanenburg.sm.dto.order;
import static com.vanenburg.sm.constants.ErrorMessage.*;

import java.util.Map;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequest {
	private Double totalPrice;
	private Map<Integer, Long> productIds;
	private Map<Integer, Double> discountedProductPrices;

	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	private String firstName;

	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	private String lastName;

	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	private String city;

	@Email(message = INCORRECT_EMAIL)
	@NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
	private String email;

	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	@Size(min = 10,max = 10,message = FILL_WITH_VALID_VALUE)
	private String phoneNumber;
}
