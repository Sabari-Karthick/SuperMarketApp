package com.vanenburg.sm.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.vanenburg.sm.domain.OrderItem;

public interface IOrderItemRepositiory extends JpaRepository<OrderItem, Long>{
	
}
