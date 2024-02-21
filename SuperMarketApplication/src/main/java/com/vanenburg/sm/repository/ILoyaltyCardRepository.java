package com.vanenburg.sm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanenburg.sm.domain.LoyaltyCard;

public interface ILoyaltyCardRepository extends JpaRepository<LoyaltyCard, Integer>{
	Optional<LoyaltyCard> findByUserId(Integer userId);
}
