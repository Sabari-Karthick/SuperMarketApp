package com.vanenburg.sm.dto.discount;

import java.time.LocalDate;
import java.util.Set;

import com.vanenburg.sm.enums.Category;
import com.vanenburg.sm.enums.DiscountType;

import lombok.Data;


@Data
public class CreateDiscount {
	private String name;
	private DiscountType type;
	private double value;
	private boolean applicableToOrder;
	private Set<Category> applicableCategory;
	private LocalDate startDate;
	private LocalDate endDate;
}
