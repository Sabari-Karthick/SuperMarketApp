package com.vanenburg.sm.service.impl;

import static com.vanenburg.sm.constants.ErrorMessage.PRODUCT_NOT_FOUND;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.dto.product.FullProductResponse;
import com.vanenburg.sm.dto.product.ProductRequest;
import com.vanenburg.sm.dto.product.ProductResponse;
import com.vanenburg.sm.enums.Category;
import com.vanenburg.sm.exceptions.ApiRequestException;
import com.vanenburg.sm.exceptions.InputFieldException;
import com.vanenburg.sm.mapper.CommonMapper;
import com.vanenburg.sm.repository.IProductRepository;
import com.vanenburg.sm.service.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

	private final CommonMapper mapper;

	private final IProductRepository productRepository;

	@Override
	public FullProductResponse getProductById(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
		return mapper.convertToResponse(product, FullProductResponse.class);
	}

	@Override
	@Transactional
	public FullProductResponse saveProduct(ProductRequest product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		Product productEntity = mapper.convertToEntity(product, Product.class);

		return mapper.convertToResponse(productRepository.save(productEntity), FullProductResponse.class);
	}

	@Override
	public List<ProductResponse> getProductByIds(List<Integer> productIDs) {
		List<Product> productByIds = productRepository.findAllById(productIDs);
		if(productByIds.isEmpty()) {
			throw new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		return productByIds.stream().map(product -> {
			ProductResponse productResponse = mapper.convertToResponse(product, ProductResponse.class);
			return productResponse;
		}).collect(Collectors.toList());
		
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> allProducts = productRepository.findAll();
		if(allProducts.isEmpty()) {
			throw new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		return allProducts.stream().map(product -> {
			ProductResponse productResponse = mapper.convertToResponse(product, ProductResponse.class);
			return productResponse;
		}).collect(Collectors.toList());
		
	}

	@Override
	public List<ProductResponse> getProductByCategory(List<Category> category) {
		List<Product> allProducts = productRepository.findByCategoryIn(category);
		if(allProducts.isEmpty()) {
			throw new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		return allProducts.stream().map(product -> {
			ProductResponse productResponse = mapper.convertToResponse(product, ProductResponse.class);
			return productResponse;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public String deleteProduct(Integer productID) {
		Product product = productRepository.findById(productID)
				.orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
		productRepository.delete(product);
		return "Product is deleted SuccessFully";
	}

	@Override
	public List<FullProductResponse> getShortagedProducts() {
		List<Product> allProducts = productRepository.findByLowStockProducts();
		if(allProducts.isEmpty()) {
			throw new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		return allProducts.stream().map(product -> {
			FullProductResponse productResponse = mapper.convertToResponse(product, FullProductResponse.class);
			return productResponse;
		}).collect(Collectors.toList());
		
	}

	@Override
	public List<FullProductResponse> getExpiredProducts() {
		List<Product> allProducts = productRepository.findByExpiredProducts(LocalDate.now());
		if(allProducts.isEmpty()) {
			throw new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		return allProducts.stream().map(product -> {
			FullProductResponse productResponse = mapper.convertToResponse(product, FullProductResponse.class);
			return productResponse;
		}).collect(Collectors.toList());
		
	}

}
