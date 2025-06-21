package com.adrasha.auth.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adrasha.auth.dto.AuthTokenResponse;
import com.adrasha.auth.dto.LoginRequest;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.RegistrationRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.exception.UserAlreadyExistsException;
import com.adrasha.auth.exception.UserNotFoundException;
import com.adrasha.auth.model.User;
import com.adrasha.auth.repository.UserRepository;
import com.adrasha.auth.service.AuthService;
import com.adrasha.auth.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO register(RegistrationRequest registrationRequest) throws UserAlreadyExistsException {

		Optional<User> existingUser = userRepository.findByUsername(registrationRequest.getUsername());

		if (existingUser.isPresent()) {
			throw new UserAlreadyExistsException("User Already Exists");
		}

		String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());

		User user = modelMapper.map(registrationRequest, User.class);
		user.setPassword(hashedPassword);

		user = userRepository.save(user);

		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public AuthTokenResponse login(LoginRequest loginRequest) throws UserNotFoundException {

		 Authentication authentication = authenticationManager.authenticate(
		 new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
		 loginRequest.getPassword()));
		
		 SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UserNotFoundException(loginRequest.getUsername()));

		JwtUser jwtUser = modelMapper.map(user, JwtUser.class);

		String token = jwtUtil.generateToken(jwtUser);

		return AuthTokenResponse.builder()
				.user(jwtUser)
				.accessToken(token)
				.tokenType("Bearer")
				.expiresIn(jwtUtil.getExpiration())
				.exp(Instant.now().plusMillis(jwtUtil.getExpiration()))
				.build();
	}

	@Override
	public UserDTO resetPassword(JwtUser jwtUser, PasswordResetRequest passwordResetRequest) {

		User user = getUserByUsername(jwtUser.getUsername());

		if (!passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())) {
			throw new BadCredentialsException("Old Password Not Matched.");
		}

		if (passwordEncoder.matches(passwordResetRequest.getNewPassword(), user.getPassword())) {
			throw new IllegalArgumentException("New password must be different from the current password.");
		}

		String hashedPassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
		user.setPassword(hashedPassword);

		userRepository.save(user);

		return modelMapper.map(user, UserDTO.class);
	}
	
	public User getUserByUsername(String username) {
		
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new BadCredentialsException("User Not Registered"));
	}
	
	public User getUserById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
	}

	@Override
	public void deleteCurrentUser(JwtUser jwtUser) {

		User user = getUserByUsername(jwtUser.getUsername());
		userRepository.delete(user);
	}

	@Override
	public void deleteUser(UUID id) {

		User user = getUserById(id);
		userRepository.delete(user);
	}

}
