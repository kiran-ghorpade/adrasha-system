package com.adrasha.auth.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 50, message = "{username.size}")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, max = 100, message = "{password.size}")
	private String password;
	
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotEmpty(message="{roles.notblank}")
	private Set<Role> roles = Set.of(Role.USER);
	
	@Enumerated(EnumType.STRING)
	private AccountStatus status = AccountStatus.PENDING;

	@Column(nullable = true)
	private Instant lastPasswordReset;

	@CreatedDate
	@Column(updatable = false)
	private Instant createdAt;

	@LastModifiedDate
	private Instant updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return this.getRoles()
	    			.stream()
	    			.map(Role::name)
	    			.map(SimpleGrantedAuthority::new)
	    			.toList();
	}

}
