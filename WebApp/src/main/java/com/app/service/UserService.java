package com.app.service;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService  {


	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Create
	public void addUser(User user) {
		userRepository.save(user);
	}

	//Retrieve
	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

	public User getUserByUsername(String username) {
		return userRepository.getUserByCredential_Username(username);
	}

	public List<User> getAllUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	public List<User> findAllByProperty(Property property) {
		return userRepository.findAllByProperty(property);
	}

	public Page<User> getUsersPaginated(Pageable pageable, List<User> users) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
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

	// Delete
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}




}
