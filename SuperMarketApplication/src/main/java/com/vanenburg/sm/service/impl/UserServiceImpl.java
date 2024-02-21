package com.vanenburg.sm.service.impl;

import static com.vanenburg.sm.constants.ErrorMessage.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.vanenburg.sm.domain.LoyaltyCard;
import com.vanenburg.sm.domain.User;
import com.vanenburg.sm.dto.auth.AuthRequest;
import com.vanenburg.sm.dto.auth.AuthResponse;
import com.vanenburg.sm.dto.card.LoyaltyCardResponse;
import com.vanenburg.sm.dto.user.BasicUserResponse;
import com.vanenburg.sm.dto.user.UserRegistrationRequest;
import com.vanenburg.sm.dto.user.UserResponse;
import com.vanenburg.sm.exceptions.ApiRequestException;
import com.vanenburg.sm.exceptions.CardException;
import com.vanenburg.sm.exceptions.EmailException;
import com.vanenburg.sm.exceptions.InputFieldException;
import com.vanenburg.sm.mapper.CommonMapper;
import com.vanenburg.sm.repository.ILoyaltyCardRepository;
import com.vanenburg.sm.repository.IUserRepository;
import com.vanenburg.sm.security.jwt.JwtProvider;
import com.vanenburg.sm.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

	private final IUserRepository userRepository;

	private final CommonMapper mapper;

	private final AuthenticationManager authenticationManager;

	private final ILoyaltyCardRepository cardRepository;

	private final JwtProvider proivder;

	private final PasswordEncoder encoder;

	@Override
	@Transactional
	public String registerUser(UserRegistrationRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		User user = mapper.convertToEntity(request, User.class);
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new EmailException(EMAIL_IN_USE);
		}
		user.setPassword(encoder.encode(request.getPassword()));
		user.setLoyaltyRegistered(false);
		userRepository.save(user);
		return "USER SUCCESSFULLY REGISTERED";
	}

	@Override
	public UserResponse getUserById(Integer userID) {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		return mapper.convertToResponse(user, UserResponse.class);
	}

	@Override
	@Transactional
	public String deleteUserById(Integer userID) {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		userRepository.delete(user);
		return "USER DELETED";
	}

	@Override
	@Transactional
	public LoyaltyCard addLoyaltyCard(Integer userID) {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (user.isLoyaltyRegistered()) {
			throw new CardException(CARD_IN_USE);
		}

		user.setLoyaltyRegistered(true);

		User registeredUser = userRepository.save(user);
		LoyaltyCard loyaltyCard = LoyaltyCard.builder().loyaltyCardNumber(UUID.randomUUID().toString()).points(500)
				.user(registeredUser).build();

		return cardRepository.save(loyaltyCard);

	}

	@Override
	public LoyaltyCardResponse getLoyaltyCardOfUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (!user.isLoyaltyRegistered()) {
			throw new CardException(CARD_NOT_FOUND + "FOR USER ID:: " + userId);
		}
		LoyaltyCard loyaltyCard = cardRepository.findByUserId(userId)
				.orElseThrow(() -> new CardException(CARD_NOT_FOUND + "FOR USER ID:: " + userId));

		return mapper.convertToResponse(loyaltyCard, LoyaltyCardResponse.class);
	}

	@Override
	public List<BasicUserResponse> getAllUsers() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(user -> {
			BasicUserResponse userResponse = mapper.convertToResponse(user, BasicUserResponse.class);
			return userResponse;
		}).collect(Collectors.toList());
	}

	@Override
	public AuthResponse login(AuthRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = userRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
			String token = proivder.createToken(request.getEmail(), user.getRoles().iterator().next().toString());
			AuthResponse authResponse = new AuthResponse();
			authResponse.setUser(mapper.convertToResponse(user, UserResponse.class));
			authResponse.setToken(token);
			return authResponse;
		} catch (AuthenticationException e) {
			throw new ApiRequestException(INCORRECT_CREDENTIALS, HttpStatus.FORBIDDEN);
		}
	}

	@Override
	@Transactional
	public LoyaltyCardResponse addLoyaltyPoints(Integer userId, Double price) {
		if (price < 10) {
			throw new ApiRequestException(LOYALTY_CONDITION_NOT_MET, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		int pointsGained = (int) (price / 10);

		LoyaltyCard loyaltyCard = cardRepository.findByUserId(userId)
				.orElseThrow(() -> new CardException(CARD_NOT_FOUND + "FOR USER ID:: " + userId));
		loyaltyCard.setPoints(loyaltyCard.getPoints() + pointsGained);
		LoyaltyCard updatedCard = cardRepository.save(loyaltyCard);
		return mapper.convertToResponse(updatedCard, LoyaltyCardResponse.class);
	}

}
