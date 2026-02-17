package com.restaurant.services.auth;

import java.util.List;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;

public interface AuthService {

	UserDto createUser(SignupRequest signupRequest);
	

}
