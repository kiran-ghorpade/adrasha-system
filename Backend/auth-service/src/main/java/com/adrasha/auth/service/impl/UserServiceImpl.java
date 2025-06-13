package com.adrasha.auth.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adrasha.auth.dto.JwtUser;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.exception.UserNotFoundException;
import com.adrasha.auth.model.User;
import com.adrasha.auth.repository.UserRepository;
import com.adrasha.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO resetPassword(JwtUser jwtUser, PasswordResetRequest passwordResetRequest) {
		
		User user = getUserByUsername(jwtUser.getUsername());

		if(!passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())) {
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
	
	@Override
	public void deleteCurrentUser(JwtUser jwtUser) {
		
		User user = getUserByUsername(jwtUser.getUsername());
		userRepository.delete(user);
	}

	@Override
	public void deleteUser(UUID id) {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User Not Found"));
		userRepository.delete(user);
	}
	
	public User getUserByUsername(String username) {

		return userRepository.findByUsername(username)
						.orElseThrow(()-> new BadCredentialsException("User Not Authenticated"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return getUserByUsername(username);
	}

}
