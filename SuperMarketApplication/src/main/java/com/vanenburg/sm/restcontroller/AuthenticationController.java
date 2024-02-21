package com.vanenburg.sm.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanenburg.sm.dto.auth.AuthRequest;
import com.vanenburg.sm.dto.user.UserRegistrationRequest;
import com.vanenburg.sm.service.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static com.vanenburg.sm.constants.PathConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(API_V1_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final IUserService userService;
	
	@PostMapping(REGISTER)
	@PreAuthorize("hasAuthority('STAFF')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request,BindingResult bindingResult) {
		return ResponseEntity.ok(userService.registerUser(request,bindingResult));
	}
	
	@PostMapping(LOGIN)
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		return ResponseEntity.ok(userService.login(request));
	}
	
	

}
