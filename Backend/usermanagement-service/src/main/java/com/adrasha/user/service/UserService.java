package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.user.exception.UserAlreadyExistsException;
import com.adrasha.user.exception.UserNotFoundException;
import com.adrasha.user.model.User;

public interface UserService {

	Page<User> getAllUsers(Pageable pageable);
    
    User getUser(UUID ashaId) throws UserNotFoundException;
    
    User createUser(User asha) throws UserAlreadyExistsException;
    
    User updateUser(UUID ashaId, User updatedAshaDetails) throws UserNotFoundException;
    
    User deleteUser(UUID ashaId) throws UserNotFoundException;
    
    User getCurrentUser(JwtUser user) throws UserNotFoundException;
}
