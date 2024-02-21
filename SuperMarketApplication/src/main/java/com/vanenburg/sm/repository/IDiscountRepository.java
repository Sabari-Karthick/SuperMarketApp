package com.vanenburg.sm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanenburg.sm.domain.Discount;



public interface IDiscountRepository extends JpaRepository<Discount, Integer>{
     Optional<Discount>  findByNameAndStartDate(String name, LocalDate startDate); 
     List<Discount> findAllByStartDate(LocalDate startDate);
}
