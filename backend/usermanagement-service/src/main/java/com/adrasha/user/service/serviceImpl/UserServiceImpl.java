package com.adrasha.user.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.user.exception.UserAlreadyExistsException;
import com.adrasha.user.exception.UserNotFoundException;
import com.adrasha.user.model.User;
import com.adrasha.user.repository.UserRepository;
import com.adrasha.user.service.UserService;

// TODO : Review class 
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<User> getAllUsers(Pageable pageable) {

		return userRepository.findAll(pageable);
	}

	@Override
	public User getUser(UUID userId) {

		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found with id : " + userId));
	}

	@Override
	public User createUser(User user) {
		
		
		Optional<User> existingUser = userRepository.findByMobileNumber(user.getMobileNumber());
		
	  	if(existingUser.isPresent()) {
	  		throw new UserAlreadyExistsException("User with mobileNumber : "+ user.getMobileNumber()+" already exist.");
	  	}

		return userRepository.save(user);
	}

	@Override
	public User updateUser(UUID userId, User updatedUserDetails) {
		User user = getUser(userId);
		modelMapper.map(updatedUserDetails, user);
		return userRepository.save(user);
	}

	@Override
	public User deleteUser(UUID userId) {
		User user = getUser(userId);
		userRepository.delete(user);
		return user;
	}

	// TODO : Review This Method
	@Override
	public User getCurrentUser(JwtUser user) {
		
		return getUser(user.getId());
	}

}
