package com.adrasha.user.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.user.model.User;
import com.adrasha.user.repository.UserRepository;
import com.adrasha.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<User> getAllUsers(Example<User> example, Pageable pageable) {

		return userRepository.findAll(example, pageable);
	}

	@Override
	public User getUser(UUID userId) {

		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User Not Found with id : " + userId));
	}

	@Override
	public User createUser(User user) {

		Optional<User> existingUser = userRepository.findById(user.getUserId());

		if (existingUser.isPresent()) {
			throw new AlreadyExistsException("User with id : " + user.getUserId() + " already exist.");
		}

		return userRepository.save(user);
	}

	@Override
	public User updateUser(UUID userId, User updatedUserDetails) {
		User user = getUser(userId);
		modelMapper.map(updatedUserDetails, user);
		return userRepository.save(user);
	}

	@Override
	public User deleteUser(UUID userId) {
		User user = getUser(userId);
		userRepository.delete(user);
		return user;
	}

	@Override
	public Map<String, Integer> getRoleDistribution() {

		Map<String, Integer> roleDistribution = new HashMap<>();
		Page<User> dataPage;

		int page = 0, size = 100;

		Sort sort = Sort.by(Direction.DESC, "name");

		do {
			Pageable pageable = PageRequest.of(page, size, sort);
			dataPage = userRepository.findAll(null, pageable);

			for (User dto : dataPage.getContent()) {
				for (String role : dto.getRoles()) {
					roleDistribution.put(role, roleDistribution.getOrDefault(role, 0) + 1);
				}
			}
			page++;
		} while (dataPage.hasNext());

		return roleDistribution;
	}

}
