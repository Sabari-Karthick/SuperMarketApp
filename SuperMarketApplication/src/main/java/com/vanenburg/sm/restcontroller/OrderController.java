package com.vanenburg.sm.restcontroller;

import static com.vanenburg.sm.constants.PathConstants.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanenburg.sm.dto.order.OrderRequest;
import com.vanenburg.sm.service.IDiscountService;
import com.vanenburg.sm.service.IOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;






@RestController
@RequestMapping(API_V1_ORDER)
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('STAFF')")
public class OrderController {

	private final IOrderService orderService;
	
	private final IDiscountService discountService;
	
	
	@PostMapping
	public ResponseEntity<?> postOrder(@Valid @RequestBody OrderRequest entity,BindingResult bindingResult) {
		
		return ResponseEntity.ok(orderService.postOrder(entity, bindingResult));
	}
	
	@GetMapping(ORDER_ID)
	public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@GetMapping(ORDER_ID_ITEMS)
	public ResponseEntity<?> getOrderItems(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderItems(orderId));
	}
	
	@PatchMapping(ORDER_ID_REFUND)
	public ResponseEntity<?> refundOrder(@PathVariable Long orderId){
		return ResponseEntity.ok(orderService.refundOrder(orderId));
	}
	
	@GetMapping(APPLY_DISCOUNTS)
	public ResponseEntity<?> getDiscountedPriceOfProducts(@RequestBody List<Integer> productIds,@PathVariable String discountName) {
		return ResponseEntity.ok(discountService.getDisCountedPriceOfProducts(productIds, discountName));
	}
	
	@GetMapping(APPLY_DISCOUNTS_ON_TOTAL)
	public ResponseEntity<?> getDiscountedPriceOfTotal(@PathVariable String discountName,@PathVariable Double totalPrice) {
		return ResponseEntity.ok(discountService.getDiscountedTotalPrice(totalPrice, discountName));
	}
	
	
	@GetMapping(CHECK_DISCOUNTS)
	public ResponseEntity<?> getTodayAvailableDiscounts() {
		return ResponseEntity.ok(discountService.getAllAvailableDiscounts());
	}
	
	
	@GetMapping(REVENUE)
	public ResponseEntity<?> getRevenueInRange(@RequestParam("from")LocalDate startDate ,@RequestParam("to")LocalDate endDate) {
		return ResponseEntity.ok(orderService.getTotalRevenueInInterval(startDate, endDate));
	}
	
	@GetMapping(STATS)
	public ResponseEntity<?> getTopSellingProducts() {
		return ResponseEntity.ok(orderService.getTopSellingProducts());
	}
	
	
}
