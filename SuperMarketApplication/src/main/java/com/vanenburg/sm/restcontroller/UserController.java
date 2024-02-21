package com.vanenburg.sm.restcontroller;

import static com.vanenburg.sm.constants.PathConstants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanenburg.sm.service.IDiscountService;
import com.vanenburg.sm.service.IProductService;
import com.vanenburg.sm.service.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(API_V1_USER)
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('STAFF', 'USER')")
public class UserController {

	private final IUserService userService;

	private final IProductService productService;
	
	private final IDiscountService discountService ;

	@GetMapping(USER_ID)
	public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.deleteUserById(userId));
	}

	@PostMapping(ADD_LOYALTY_CARD)
	public ResponseEntity<?> registerLoyaltyCard(@PathVariable Integer userId) {

		return ResponseEntity.ok(userService.addLoyaltyCard(userId));
	}

	@GetMapping(LOYALTY_CARD)
	public ResponseEntity<?> getCardOfUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getLoyaltyCardOfUser(userId));
	}

	@GetMapping(ALL)
	public ResponseEntity<?> getAllUser() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PostMapping(CART)
	public ResponseEntity<?> getCart(@RequestBody List<Integer> productIds) {
		return ResponseEntity.ok(productService.getProductByIds(productIds));
	}

	@GetMapping(APPLY_POINTS)
	public ResponseEntity<?> applyLoyaltyPoints(@RequestParam("total") Double totalPrice,
			@PathVariable Integer userId) {
		return ResponseEntity.ok(discountService.discountedPriceByLoyalty(totalPrice, userId));
	}

	@PatchMapping(ADD_POINTS)
	public ResponseEntity<?> addLoyaltyPoints(@PathVariable Integer userId, @RequestParam("price") Double price) {

		return ResponseEntity.ok(userService.addLoyaltyPoints(userId, price));
	}

}
