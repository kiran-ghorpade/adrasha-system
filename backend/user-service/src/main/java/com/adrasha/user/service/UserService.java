package com.adrasha.user.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.model.Role;
import com.adrasha.user.model.User;

public interface UserService {

	Page<User> getAllUsers(Example<User> example, Pageable pageable);
    
    User getUser(UUID id) throws NotFoundException;
    
    User createUser(User user) throws AlreadyExistsException;
    
    User updateUser(UUID userId, User updatedUser) throws NotFoundException;
    
    User deleteUser(UUID userId) throws NotFoundException;

	Map<Role, Long> getRoleDistribution();

	long getTotalUserCount(Example<User> example);

	void removeRoleFromUser(UUID id, String role);
 }
