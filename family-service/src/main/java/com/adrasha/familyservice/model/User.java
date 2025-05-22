package com.adrasha.familyservice.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private UUID id;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 50, message = "{username.size}")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, max = 100, message = "{password.size}")
	private String password;
	
	@NotBlank(message="{roles.notblank}")
	private String role;

	private Instant lastPasswordReset;

	private Instant createdAt;

	private Instant updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority(this.role));
	}

}
