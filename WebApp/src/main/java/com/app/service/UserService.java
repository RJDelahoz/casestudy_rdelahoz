package com.app.service;


import com.app.model.Credential;
import com.app.model.User;
import com.app.repo.CredentialRepository;
import com.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {


	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user) {
		userRepository.save(user);
	}

	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

}
