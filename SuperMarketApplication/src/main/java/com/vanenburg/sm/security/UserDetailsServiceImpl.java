package com.vanenburg.sm.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static com.vanenburg.sm.constants.ErrorMessage.*;
import com.vanenburg.sm.domain.User;
import com.vanenburg.sm.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(usermail).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
		return new UserPrincipal(user);
	}

}
