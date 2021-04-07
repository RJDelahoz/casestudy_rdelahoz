package com.app.service;

import com.app.model.User;
import com.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	// Delete
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}


}
