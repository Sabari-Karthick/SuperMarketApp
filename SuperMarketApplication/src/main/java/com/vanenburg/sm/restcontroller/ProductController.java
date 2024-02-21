package com.vanenburg.sm.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanenburg.sm.enums.Category;
import com.vanenburg.sm.service.IProductService;

import lombok.RequiredArgsConstructor;

import static com.vanenburg.sm.constants.PathConstants.*;

import java.util.List;

@RestController
@RequestMapping(API_V1_PRODUCTS)
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('STAFF')")
public class ProductController {

	private final IProductService productService;

	@GetMapping(PRODUCT_ID)
	public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
		return ResponseEntity.ok(productService.getProductById(productId));
	}

	@PostMapping(IDS)
	public ResponseEntity<?> getProductByIds(@RequestBody List<Integer> ids) {
		return ResponseEntity.ok(productService.getProductByIds(ids));
	}

	@GetMapping(ALL)
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PostMapping(SEARCH_CATEGORY)
	public ResponseEntity<?> getProductByCategories(@RequestBody List<Category> categories) {
		return ResponseEntity.ok(productService.getProductByCategory(categories));
	}

	@GetMapping(CHECK_STOCK)
	public ResponseEntity<?> getAllLowStockProducts() {
		return ResponseEntity.ok(productService.getShortagedProducts());
	}

	@GetMapping(CHECK_EXPIRY)
	public ResponseEntity<?> getAllExpiredProducts() {
		return ResponseEntity.ok(productService.getExpiredProducts());
	}

}
