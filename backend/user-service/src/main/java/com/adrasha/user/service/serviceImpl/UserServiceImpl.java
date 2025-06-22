package com.adrasha.user.service.serviceImpl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.model.Role;
import com.adrasha.user.integration.AuthService;
import com.adrasha.user.model.User;
import com.adrasha.user.repository.UserRepository;
import com.adrasha.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<User> getAllUsers(Example<User> example, Pageable pageable) {
		
		return userRepository.findAll(example, pageable);
	}

	@Override
	public User getUser(UUID userId) {

		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User Not Found with id : " + userId));
	}

	@Override
	public User createUser(User user) {

		if(userRepository.existsById(user.getUserId())) {
			
			throw new AlreadyExistsException("User with id : " + user.getUserId() + " already exist.");
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
		
		authService.deleteUserDetails(userId);
		
		userRepository.delete(user);
		return user;
	}

	@Override
	public long getTotalUserCount(Example<User> example) {
		return userRepository.count(example);
	}

	@Override
	public void removeRoleFromUser(UUID id, Role role) {
		JwtUser user = authService.removeRole(id,role);
		
		Set<Role> roles =  user.getRoles().stream()
								.map(Role::valueOf)
								.collect(Collectors.toSet());
		
		User updatedUser = modelMapper.map(user, User.class);
		updatedUser.setRoles(roles);
		this.updateUser(id, updatedUser);
	}

}
