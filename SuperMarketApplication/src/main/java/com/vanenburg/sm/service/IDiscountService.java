package com.vanenburg.sm.service;

import java.util.List;
import java.util.Map;

import com.vanenburg.sm.domain.Discount;
import com.vanenburg.sm.dto.discount.CreateDiscount;

public interface IDiscountService {
      Double discountedPriceByLoyalty(Double totalPrice,Integer userId);
      Discount createNewDiscount(CreateDiscount discount);
      Map<Integer, Double> getDisCountedPriceOfProducts(List<Integer> productIds,String discountName);
      List<Discount> getAllAvailableDiscounts();
      Double getDiscountedTotalPrice(Double totalPrice,String discountName);
}

