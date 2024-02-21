package com.vanenburg.sm.dto.product;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullProductResponse extends ProductResponse{
	
	private int minStockThreshold;
	private LocalDate expirationDate;
}
