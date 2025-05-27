package com.adrasha.authservice.service.impl;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adrasha.authservice.dto.AuthTokenResponse;
import com.adrasha.authservice.dto.JwtUser;
import com.adrasha.authservice.dto.LoginRequest;
import com.adrasha.authservice.dto.PasswordResetRequest;
import com.adrasha.authservice.dto.RegistrationRequest;
import com.adrasha.authservice.dto.UserDTO;
import com.adrasha.authservice.exception.UserAlreadyExistsException;
import com.adrasha.authservice.exception.UserNotFoundException;
import com.adrasha.authservice.model.User;
import com.adrasha.authservice.service.AuthService;
import com.adrasha.authservice.service.UserService;
import com.adrasha.authservice.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO register(RegistrationRequest registrationRequest) throws UserAlreadyExistsException{
		
		service.getUserByUsername(registrationRequest.getUsername());		
		
		String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
		
		User user = modelMapper.map(registrationRequest, User.class);
		user.setPassword(hashedPassword);
		
		user = service.addUser(user);
		
		return modelMapper.map(user, UserDTO.class);
	}
	

	@Override
	public AuthTokenResponse login(LoginRequest loginRequest) throws UserNotFoundException {
		
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            User user = service.getUserByUsername(authentication.getName());
            
            JwtUser jwtUser = modelMapper.map(user, JwtUser.class);
            
            String token = jwtUtil.generateToken(jwtUser);
		
        return AuthTokenResponse.builder()
        		.accessToken(token)
        		.tokenType("Bearer")
        		.expiresIn(jwtUtil.getExpiration())
        		.exp(Instant.now().plusMillis(jwtUtil.getExpiration()))
				.build();
        }


	@Override
	public UserDTO resetPassword(PasswordResetRequest passwordResetRequest) {
	     
		User user = service.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());		
		return modelMapper.map(service.updateUser(user.getId(), user), UserDTO.class);
	}
	
}
