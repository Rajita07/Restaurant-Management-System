package com.restaurant.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.enums.UserRole;
import com.restaurant.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImplementation implements AuthService {
	private final UserRepository userRepo;
	
	public AuthServiceImplementation(UserRepository userRepo) {

		this.userRepo = userRepo;
	}
	@PostConstruct               //call this method at time of application running
	public void createAdminAccount() {
		User adminAccount=userRepo.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			User user=new User();
			user.setName("admin");
			user.setEmail("admin123@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setUserRole(UserRole.ADMIN);
			userRepo.save(user);
		}
				
	}

	
	

	@Override
	public UserDto createUser(SignupRequest signupRequest) {
		User user=new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createdUser=userRepo.save(user);
		UserDto createdUserdto=new UserDto();
		createdUserdto.setId(createdUser.getId());
		createdUserdto.setName(createdUser.getName());
		createdUserdto.setEmail(createdUser.getEmail());
		createdUserdto.setUserRole(createdUser.getUserRole());
		return createdUserdto;
		
	}
	
	
	

}
