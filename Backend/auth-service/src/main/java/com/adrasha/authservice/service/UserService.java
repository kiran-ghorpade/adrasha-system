package com.adrasha.authservice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.adrasha.authservice.model.User;

public interface UserService extends UserDetailsService{

	public Page<User> getAllUsers(Pageable pageable);
	public User getUser(UUID id);
	public Optional<User> getUserByUsername(String username);
	public User addUser(User user);
	public User updateUser(UUID id, User user);
	public void deleteUser(UUID id);
	public User getCurrentUser(Authentication authentication);
}
