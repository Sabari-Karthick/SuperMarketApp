package com.vanenburg.sm.service.impl;

import static com.vanenburg.sm.constants.ErrorMessage.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.vanenburg.sm.domain.Order;
import com.vanenburg.sm.domain.OrderItem;
import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.dto.order.OrderItemResponse;
import com.vanenburg.sm.dto.order.OrderRequest;
import com.vanenburg.sm.dto.order.OrderResponse;
import com.vanenburg.sm.enums.Status;
import com.vanenburg.sm.exceptions.ApiRequestException;
import com.vanenburg.sm.exceptions.InputFieldException;
import com.vanenburg.sm.mapper.CommonMapper;
import com.vanenburg.sm.repository.IOrderItemRepositiory;
import com.vanenburg.sm.repository.IOrderRepository;
import com.vanenburg.sm.repository.IProductRepository;
import com.vanenburg.sm.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

	private final IOrderRepository orderRepository;

	private final IProductRepository productRepository;

	private final IOrderItemRepositiory orderItemRepository;

	private final CommonMapper mapper;

	@Override
	@Transactional
	public OrderResponse postOrder(OrderRequest orderRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		ArrayList<OrderItem> orderItemList = new ArrayList<>();
		Map<Integer, Double> discountedProductPrices = orderRequest.getDiscountedProductPrices();
		for (Entry<Integer, Long> entry : orderRequest.getProductIds().entrySet()) {
			Product product = productRepository.findById(entry.getKey())
					.orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
			if (product.getQuantity() < entry.getValue()) {
				throw new ApiRequestException(STOCK_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
			product.setQuantity((int) (product.getQuantity() - entry.getValue()));
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setDiscountedProductPrice(discountedProductPrices.get(entry.getKey()));
			orderItem.setAmount((long) (discountedProductPrices.get(entry.getKey()) * entry.getValue()));
			orderItem.setQuantity(entry.getValue());
			orderItemList.add(orderItem);
			orderItemRepository.save(orderItem);
		}
		Order order = mapper.convertToEntity(orderRequest, Order.class);
		order.setOrderItems(orderItemList);
		order.setStatus(Status.PAYED);
		order.setDate(LocalDate.now());
		Order savedOrder = orderRepository.save(order);
		return mapper.convertToResponse(savedOrder, OrderResponse.class);
	}

	@Override
	public OrderResponse getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
		return mapper.convertToResponse(order, OrderResponse.class);
	}

	@Override
	public List<OrderItemResponse> getOrderItems(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
		return order.getOrderItems().stream().map(orderItem -> {
			return mapper.convertToResponse(orderItem, OrderItemResponse.class);
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public String refundOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			Product product = orderItem.getProduct();
			product.setQuantity((int) (product.getQuantity() + orderItem.getQuantity()));
			productRepository.save(product);

		}
		order.setStatus(Status.REFUNDED);
		orderRepository.save(order);

		return "SUCCESSFULLY REFUNDED";

	}

	@Override
	public Double getTotalRevenueInInterval(LocalDate startDate, LocalDate endDate) {
		return orderRepository.getTotalRevenueByDateRange(startDate, endDate);
	}

	@Override
	public List<Object[]> getTopSellingProducts() {
		return orderRepository.getTopThreeSellingProducts();
	}

}
