package com.vanenburg.sm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vanenburg.sm.domain.Order;


public interface IOrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.date BETWEEN :startDate AND :endDate")
	Double getTotalRevenueByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(nativeQuery = true, value = "SELECT p.id, p.product_title, SUM(oi.quantity) AS total_quantity  FROM order_item oi  JOIN product_details p ON oi.product_id=p.id GROUP BY p.id,p.product_title ORDER BY total_quantity DESC LIMIT 3")
	List<Object[]> getTopThreeSellingProducts();
}
