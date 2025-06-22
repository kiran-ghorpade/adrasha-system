package com.adrasha.user.integration;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.model.Role;
import com.adrasha.user.client.AuthClient;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;
import com.adrasha.user.model.RoleRequest;

@Service
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
				throw new RuntimeException("Error During Interservice Call adding role");
			}
			
			return user;
	}
	
	public JwtUser removeRole(UUID id, Role role) {
		JwtUser user = authClient.removeRole(RoleUpdateDTO.builder()
								.userId(id)
								.role(role)
								.build()
							);
		
			if(user==null) {
				throw new RuntimeException("Error During Interservice Call for removing role");
			}
			
			return user;	
	}

	public void deleteUserDetails(UUID userId) {
		  if(!authClient.deleteUser(userId).getStatusCode().is2xxSuccessful()) {
				throw new RuntimeException("Error During Interservice Call For Delete");
		  }
	}
	
	
}
