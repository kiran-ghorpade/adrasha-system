package com.adrasha.authservice.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adrasha.authservice.exception.UserNotFoundException;
import com.adrasha.authservice.model.User;
import com.adrasha.authservice.repository.UserRepository;
import com.adrasha.authservice.service.UserService;

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper; 

	
	@Override
	public User getUser(UUID id) {
		return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UUID id, User userDTO) {
		User existingUser = getUser(id);

		if (Optional.of(existingUser).isPresent()) {
			User user = existingUser;
			mapper.map(userDTO, user);
			return userRepository.save(user);
		}

		return null;
	}

	@Override
	public void deleteUser(UUID id) {
		getUser(id);
		userRepository.deleteById(id);
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UserNotFoundException(username));
		return user;
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

}
