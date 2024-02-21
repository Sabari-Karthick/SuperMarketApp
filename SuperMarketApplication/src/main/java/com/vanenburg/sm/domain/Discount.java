package com.vanenburg.sm.domain;

import java.time.LocalDate;
import java.util.Set;

import com.vanenburg.sm.enums.Category;
import com.vanenburg.sm.enums.DiscountType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	@Enumerated(EnumType.STRING)
	private DiscountType type;
	private double value;
	private boolean applicableToOrder;
	private LocalDate startDate;
	private LocalDate endDate;

	@ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "discount_categories", joinColumns = @JoinColumn(name = "discount_id", referencedColumnName = "id"))
	@Enumerated(EnumType.STRING)
	private Set<Category> applicableCategory;

}
