package com.restaurant.controllers;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.AuthenticationRequest;
import com.restaurant.dtos.AuthenticationResponse;
import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.repository.UserRepository;
import com.restaurant.services.auth.AuthService;
import com.restaurant.services.jwt.UserService;
import com.restaurant.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService ;
	
	private final AuthenticationManager authenticationManager ;
	
	private final UserService userService;
	
	private final JwtUtil jwtUtil;
	
	private  UserRepository userRepository;
	
	
	
	public AuthController(
            AuthService authService,
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtUtil jwtUtil, UserRepository userRepository) {

        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
		
    }
	@PostMapping("/signup")
	public ResponseEntity<?> signUpUser(@RequestBody SignupRequest signupRequest){
		UserDto createdUserDto=  authService.createUser(signupRequest);
		
		if(createdUserDto==null) {
			return new ResponseEntity<>("User Not Created.Come again later" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	
		
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(
	        @RequestBody AuthenticationRequest authenticationRequest) {

	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                authenticationRequest.getEmail(),
	                authenticationRequest.getPassword()
	            )
	        );
	    } catch (BadCredentialsException e) {
	        return ResponseEntity
	                .status(HttpStatus.UNAUTHORIZED)
	                .body("Incorrect email or password");
	    }

	    final UserDetails userDetails =
	            userService
	                    .userDetailsService()
	                    .loadUserByUsername(authenticationRequest.getEmail());

	   final String jwt = jwtUtil.generateToken(userDetails);


	    Optional<User> optionalUser =
	            userRepository.findByEmail(userDetails.getUsername());

	    if (optionalUser.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("User not found");
	    }

	    User user = optionalUser.get();

	    AuthenticationResponse authenticationResponse =
	            new AuthenticationResponse();

	    authenticationResponse.setJwt(jwt);
	    authenticationResponse.setUserId(user.getId());
	    authenticationResponse.setUserRole(user.getUserRole());

	    return ResponseEntity.ok(authenticationResponse);
	}


}
