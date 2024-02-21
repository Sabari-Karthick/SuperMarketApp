package com.vanenburg.sm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vanenburg.sm.domain.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

}
