package com.vanenburg.sm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.enums.Category;

public interface IProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByCategoryIn(List<Category> category);
	
	@Query("SELECT p FROM Product p WHERE p.quantity < p.minStockThreshold")
	List<Product> findByLowStockProducts();
	
	@Query("SELECT p FROM Product p WHERE p.expirationDate < :expiryDate")
	List<Product> findByExpiredProducts(LocalDate expiryDate);
	
	
}
