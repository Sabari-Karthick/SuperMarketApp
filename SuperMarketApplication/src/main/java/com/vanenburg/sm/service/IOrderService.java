package com.vanenburg.sm.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.vanenburg.sm.dto.order.OrderItemResponse;
import com.vanenburg.sm.dto.order.OrderRequest;
import com.vanenburg.sm.dto.order.OrderResponse;

public interface IOrderService {
	OrderResponse postOrder(OrderRequest orderRequest, BindingResult bindingResult);
	OrderResponse getOrderById(Long orderId);
	List<OrderItemResponse>  getOrderItems(Long orderId);
	String refundOrder(Long orderId);
	Double getTotalRevenueInInterval(LocalDate startDate,LocalDate endDate);
	  List<Object[]> getTopSellingProducts();
}
