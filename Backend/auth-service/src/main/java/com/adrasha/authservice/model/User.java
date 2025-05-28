package com.adrasha.authservice.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adrasha.authservice.convertor.UpperCaseConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@NotEmpty(message="{roles.notblank}")
	@Convert(converter = UpperCaseConverter.class)
	private List<String> roles = List.of("USER");
	
	@Enumerated(EnumType.STRING)
	@Convert(converter = UpperCaseConverter.class)
	private AccountStatus status = AccountStatus.PENDING;

	private Instant lastPasswordReset;

	@CreationTimestamp
	@Column(updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return this.getRoles()
	    			.stream()
	    			.map(SimpleGrantedAuthority::new)
	    			.toList();
	}

}
