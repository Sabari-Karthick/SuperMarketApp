package com.vanenburg.sm.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.vanenburg.sm.dto.product.FullProductResponse;
import com.vanenburg.sm.dto.product.ProductRequest;
import com.vanenburg.sm.dto.product.ProductResponse;
import com.vanenburg.sm.enums.Category;

public interface IProductService {
	FullProductResponse getProductById(Integer productId);

	FullProductResponse saveProduct(ProductRequest product, BindingResult bindingResult);

	List<ProductResponse> getProductByIds(List<Integer> productIDs);

	List<ProductResponse> getAllProducts();

	List<ProductResponse> getProductByCategory(List<Category> category);

	String deleteProduct(Integer productID);

	List<FullProductResponse> getShortagedProducts();

	List<FullProductResponse> getExpiredProducts();

}
