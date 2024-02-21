package com.vanenburg.sm.dto.product;

import static com.vanenburg.sm.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;
import static com.vanenburg.sm.constants.ErrorMessage.FILL_WITH_VALID_VALUE;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.vanenburg.sm.enums.Category;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
	@NotBlank(message = FILL_IN_THE_INPUT_FIELD)
	@Length(max = 25)
	private String name;
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	private Double price;
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	@Min(value = 10, message = FILL_WITH_VALID_VALUE)
	private Integer quantity;
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	private Category category;
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	@Min(value = 5, message = FILL_WITH_VALID_VALUE)
	private Integer minStockThreshold;
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	@Future(message = FILL_WITH_VALID_VALUE)
	private LocalDate expirationDate;
}
