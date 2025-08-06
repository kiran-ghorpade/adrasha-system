package com.adrasha.data.integration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.client.UserClient;

import feign.FeignException;

@Service
public class UserDataService {

	@Autowired
	private UserClient userClient;

	public void verifyUserExist(UUID userId) {
		try {
			userClient.headUser(userId);
		} catch (FeignException.NotFound e) {
			throw new NotFoundException("error.user.notFound");
		}
	}

}
