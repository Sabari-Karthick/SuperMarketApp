package com.vanenburg.sm.dto.order;

import com.vanenburg.sm.domain.Product;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long id;

    private Long amount;

    private Long quantity;
    
    private Double discountedProductPrice;

    private Product product;
}

