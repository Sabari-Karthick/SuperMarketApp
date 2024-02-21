package com.vanenburg.sm.restcontroller;

import static com.vanenburg.sm.constants.PathConstants.ADD;
import static com.vanenburg.sm.constants.PathConstants.API_V1_STAFF;
import static com.vanenburg.sm.constants.PathConstants.DELETE_BY_PRODUCT_ID;
import static com.vanenburg.sm.constants.PathConstants.DELETE_BY_USER_ID;
import static com.vanenburg.sm.constants.PathConstants.EDIT;
import static com.vanenburg.sm.constants.PathConstants.EXPORT;
import static com.vanenburg.sm.constants.PathConstants.IMPORT;
import static com.vanenburg.sm.constants.PathConstants.ADD_DISCOUNT;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vanenburg.sm.dto.discount.CreateDiscount;
import com.vanenburg.sm.dto.product.ProductRequest;
import com.vanenburg.sm.dto.product.ProductUpdateRequest;
import com.vanenburg.sm.helper.FileHelper;
import com.vanenburg.sm.service.IDiscountService;
import com.vanenburg.sm.service.IProductService;
import com.vanenburg.sm.service.IUserService;
import com.vanenburg.sm.service.impl.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(API_V1_STAFF)
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('STAFF')")
public class StaffController {

	private final IProductService productService;

	private final FileService fileService;

	private final FileHelper fileHelper;

	private final IUserService userService;
	
	private final IDiscountService discountService;

	@PostMapping(ADD)
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest product, BindingResult bindingResult) {
		return ResponseEntity.ok(productService.saveProduct(product, bindingResult));
	}

	@PutMapping(EDIT)
	public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductUpdateRequest product,
			BindingResult bindingResult) {
		return ResponseEntity.ok(productService.saveProduct(product, bindingResult));
	}

	@DeleteMapping(DELETE_BY_PRODUCT_ID)
	public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
		return ResponseEntity.ok(productService.deleteProduct(productId));
	}

	@DeleteMapping(DELETE_BY_USER_ID)
	public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.deleteUserById(userId));
	}

	@PostMapping(IMPORT)
	public ResponseEntity<?> addProducts(@RequestParam("file") MultipartFile file) {
		if (fileHelper.hasCSVFormat(file)) {
			fileService.saveProductsFromFile(file);
			return ResponseEntity.ok("SuccessFully Uploaded");
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Upload a Proper File");
	}

	@GetMapping(EXPORT)
	public ResponseEntity<?> getFile() {
		String filename = "product_details.csv";
		InputStreamResource file = new InputStreamResource(fileService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@PostMapping(ADD_DISCOUNT)
	public ResponseEntity<?> postMethodName(@RequestBody CreateDiscount discount) {
		
		return ResponseEntity.ok(discountService.createNewDiscount(discount));
	}
	

}
