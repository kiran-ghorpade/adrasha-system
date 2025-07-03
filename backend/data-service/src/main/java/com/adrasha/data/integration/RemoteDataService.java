package com.adrasha.data.integration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.client.LocationClient;
import com.adrasha.data.client.UserClient;

@Service
public class RemoteDataService {

	@Autowired
	private LocationClient locationClient;

	@Autowired
	private UserClient userClient;
	
	public void verifyUserExist(UUID userId) {
		
		boolean status = userClient.getUser(userId).getStatusCode().is2xxSuccessful();
		
		if(!status) {
			throw new NotFoundException("error.user.notFound");
		}
	
	}

	public void verifyLocationExist(UUID locationId) {
		boolean status = locationClient.getLocation(locationId).getStatusCode().is2xxSuccessful();
		
		if(!status) {
			throw new NotFoundException("error.location.notFound");
		}
	}
}
