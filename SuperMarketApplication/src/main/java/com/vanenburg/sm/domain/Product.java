package com.vanenburg.sm.domain;

import java.time.LocalDate;

import com.vanenburg.sm.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_details")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "product_title")
	private String name;
	@Column(name = "price")
	private double price;
	@Column(name = "product_quantity")
	private int quantity;
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column(name = "min_stock")
	private int minStockThreshold;
	@Column(name = "exp_date")
	private LocalDate expirationDate;

}
