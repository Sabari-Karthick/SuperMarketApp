package com.vanenburg.sm.dto.product;

import com.vanenburg.sm.enums.Category;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductResponse {
	private Integer id;
	private String name;
	private double price;
	private Category category;
	private int quantity;
}
