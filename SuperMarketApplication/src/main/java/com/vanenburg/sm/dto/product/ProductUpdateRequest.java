package com.vanenburg.sm.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import static com.vanenburg.sm.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Getter
public class ProductUpdateRequest extends ProductRequest {
	@NotNull(message = FILL_IN_THE_INPUT_FIELD)
	private Integer id;
}
