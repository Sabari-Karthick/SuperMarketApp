package com.vanenburg.sm.dto.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoyaltyCardResponse {
	private Integer id;

	private String loyaltyCardNumber;

	private Integer points;
}
