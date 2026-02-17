package com.restaurant.services.jwt;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.entities.User;
import com.restaurant.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetailsService userDetailsService() {
		
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			    User user = userRepository.findByEmail(username)
			        .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

			    return new org.springframework.security.core.userdetails.User(
			        user.getEmail(),
			        user.getPassword(),
			        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
			    );
			}

		};
	}

}
