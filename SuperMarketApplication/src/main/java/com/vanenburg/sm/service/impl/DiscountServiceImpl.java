package com.vanenburg.sm.service.impl;

import static com.vanenburg.sm.constants.ErrorMessage.CARD_NOT_FOUND;
import static com.vanenburg.sm.constants.ErrorMessage.LOYALTY_CONDITION_NOT_MET;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanenburg.sm.domain.Discount;
import com.vanenburg.sm.domain.LoyaltyCard;
import com.vanenburg.sm.domain.Product;
import com.vanenburg.sm.dto.discount.CreateDiscount;
import com.vanenburg.sm.enums.Category;
import com.vanenburg.sm.enums.DiscountType;
import com.vanenburg.sm.exceptions.ApiRequestException;
import com.vanenburg.sm.exceptions.CardException;
import com.vanenburg.sm.mapper.CommonMapper;
import com.vanenburg.sm.repository.IDiscountRepository;
import com.vanenburg.sm.repository.ILoyaltyCardRepository;
import com.vanenburg.sm.repository.IProductRepository;
import com.vanenburg.sm.service.IDiscountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements IDiscountService {

	private final ILoyaltyCardRepository cardRepository;

	private final IDiscountRepository discountRepository;

	private final IProductRepository productRepository;

	private final CommonMapper mapper;

	@Override
	@Transactional
	public Double discountedPriceByLoyalty(Double totalPrice, Integer userId) {
		if (totalPrice < 499) {
			throw new ApiRequestException(LOYALTY_CONDITION_NOT_MET, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		int pointsCanBeApplied = (int) (totalPrice / 10);
		LoyaltyCard loyaltyCard = cardRepository.findByUserId(userId)
				.orElseThrow(() -> new CardException(CARD_NOT_FOUND + "FOR USER ID:: " + userId));
		int availablePoints = loyaltyCard.getPoints();
		if (availablePoints < pointsCanBeApplied) {
			throw new ApiRequestException(LOYALTY_CONDITION_NOT_MET, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		loyaltyCard.setPoints(availablePoints - pointsCanBeApplied);
		cardRepository.save(loyaltyCard);
		return totalPrice - pointsCanBeApplied;
	}

	@Override
	@Transactional
	public Discount createNewDiscount(CreateDiscount discountRequest) {
		Discount discount = mapper.convertToEntity(discountRequest, Discount.class);
		return discountRepository.save(discount);
	}

	@Override
	public Map<Integer, Double> getDisCountedPriceOfProducts(List<Integer> productIds, String discountName) {
		HashMap<Integer, Double> prductIdAndPriceMap = new HashMap<>();
		List<Product> products = productRepository.findAllById(productIds);
		for (Product product : products) {
			Category category = product.getCategory();
			Optional<Discount> discount = discountRepository.findByNameAndStartDate(discountName, LocalDate.now());
			if (discount.isPresent() && !discount.get().isApplicableToOrder() && discount.get().getApplicableCategory().contains(category)) {
				DiscountType type = discount.get().getType();
				double value = discount.get().getValue();
				prductIdAndPriceMap.put(product.getId(), discountedPrice(type, value, product.getPrice()));
			} else {
				prductIdAndPriceMap.put(product.getId(), product.getPrice());
			}
		}

		return prductIdAndPriceMap;
	}

	@Override
	public List<Discount> getAllAvailableDiscounts() {
		return discountRepository.findAllByStartDate(LocalDate.now());
	}

	private Double discountedPrice(DiscountType type, Double value, Double price) {
		if (type.equals(DiscountType.PERCENTAGE) || type.equals(DiscountType.PROMO)) {
			double percentageValue = value / 100.0;
			double discountValue = price * percentageValue;
			return price - discountValue;
		} else {
			return price - value;
		}
	}

	@Override
	public Double getDiscountedTotalPrice(Double totalPrice, String discountName) {
		Optional<Discount> discount = discountRepository.findByNameAndStartDate(discountName, LocalDate.now());
		if(discount.isPresent()&&discount.get().isApplicableToOrder()) {
			DiscountType type = discount.get().getType();
			double value = discount.get().getValue();
			return discountedPrice(type, value, totalPrice);
		}
		return totalPrice;
	}

}
