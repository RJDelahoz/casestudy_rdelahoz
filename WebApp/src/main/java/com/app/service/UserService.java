package com.app.service;

import com.app.dao.UserDao;
import com.app.model.Property;
import com.app.model.User;
import com.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserService implements UserDao {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Create
	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	// Retrieve
	@Override
	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByCredential_Username(username);
	}

	public User getUserByUsername(String username) {
		return findUserByUsername(username).orElse(null);
	}

	@Override
	public List<User> getAllUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<User> findAllByProperty(Property property) {
		return userRepository.findAllByProperty(property);
	}

	@Override
	public Page<User> getUsersPaginated(Pageable pageable, List<User> users) {
		int pageSize = pageable.getPageSize(); // Number of  users being displayed
		int currentPage = pageable.getPageNumber(); // Current page of users to be displayed
		int startItem = currentPage * pageSize;
		List<User> userPage;

		if (users.size() < startItem) {
			userPage = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, users.size());
			userPage = users.subList(startItem, toIndex);
		}

		return new PageImpl<>(userPage, PageRequest.of(currentPage, pageSize), users.size());
	}

	// Update
	@Override
	public boolean updateUser(User user) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if (optionalUser.isPresent()) {
			userRepository.save(user);
			return true;
		}
		return false;
	}

	// Delete
	@Override
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}


}
