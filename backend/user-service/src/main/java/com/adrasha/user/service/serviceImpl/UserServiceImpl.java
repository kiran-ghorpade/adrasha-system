package com.adrasha.user.service.serviceImpl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.dto.filter.UserFilterDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.model.Role;
import com.adrasha.core.utils.ExampleMatcherUtils;
import com.adrasha.user.dto.user.UserUpdateDTO;
import com.adrasha.user.integration.AuthService;
import com.adrasha.user.model.User;
import com.adrasha.user.repository.UserRepository;
import com.adrasha.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private AuthService authService;
	private ModelMapper mapper;

	@Override
	public Page<UserResponseDTO> getUserPage(UserFilterDTO filterDTO , Pageable pageable) {
		User searchTerms = mapper.map(filterDTO, User.class);
		
		Example<User> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Page<User> page =  userRepository.findAll(example, pageable);
		
		return page.map(user-> mapper.map(user, UserResponseDTO.class));
	}

	@Override
	public Long getUserCount(UserFilterDTO filterDTO) {

		User searchTerms = mapper.map(filterDTO, User.class);
		
		Example<User> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		return userRepository.count(example);
	}
	
	@Override
	public UserResponseDTO getUser(UUID userId) {

		return userRepository.findById(userId)
				.map(user-> mapper.map(user, UserResponseDTO.class))
				.orElseThrow(() -> new NotFoundException("error.user.notFound"));
	}

	@Override
	public UserResponseDTO createUser(UserResponseDTO createDTO) {

		if(userRepository.existsByAdharNumber(createDTO.getAdharNumber())) {
			throw new AlreadyExistsException("error.user.alreadyExists");
		}

		User user = mapper.map(createDTO, User.class);
		user = userRepository.save(user);
		
		return mapper.map(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO updateUser(UUID userId, UserUpdateDTO updateDTO) {
		User user =userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("error.user.notFound"));
		
		mapper.map(updateDTO, user);
		user = userRepository.save(user);
		
		return mapper.map(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO deleteUser(UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("error.user.notFound"));
		
		authService.deleteUserDetails(userId);
		
		userRepository.delete(user);
		
		return mapper.map(user, UserResponseDTO.class);
	}

	@Override
	public void removeRoleFromUser(UUID id, Role role) {
		JwtUser user = authService.removeRole(id,role);
		
		Set<Role> roles =  user.getRoles().stream()
								.map(Role::valueOf)
								.collect(Collectors.toSet());
		
		User updatedUser = mapper.map(user, User.class);
		updatedUser.setRoles(roles);
		
		
		this.updateUser(id, mapper.map(updatedUser, UserUpdateDTO.class));
	}

}
