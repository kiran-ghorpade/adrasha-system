package com.adrasha.user.integration;


import org.springframework.beans.factory.annotation.Autowired;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.user.client.AuthClient;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;
import com.adrasha.user.model.RoleRequest;

public class AuthService {

	@Autowired
	private AuthClient authClient;
	
	public JwtUser addRole(RoleRequest request) {
			JwtUser user = authClient.addRole(RoleUpdateDTO.builder()
								.userId(request.getUserId())
								.role(request.getRole())
								.build()
							);	
					
			if(user==null) {
				throw new RuntimeException("Error During Interservice Call");
			}
			
			return user;
	}
}
