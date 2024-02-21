package com.vanenburg.sm.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.helper.FileHelper;
import com.vanenburg.sm.repository.IProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final IProductRepository productRepository;

	private final FileHelper fileHelper;

	public void saveProductsFromFile(MultipartFile file) {
		try {
			List<Product> products = fileHelper.csvToProducts(file.getInputStream());
			productRepository.saveAll(products);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	
	
	  public ByteArrayInputStream load() {
		      List<Product> list = productRepository.findAll();

		    ByteArrayInputStream in = fileHelper.productsToCSV(list);
		    return in;
		  }
}
