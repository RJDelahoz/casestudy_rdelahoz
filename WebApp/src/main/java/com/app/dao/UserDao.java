package com.app.dao;

import com.app.model.Property;
import com.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface UserDao {

	// Create
	void addUser(User user);

	//Retrieve
	Optional<User> getUserById(long id);

	Optional<User> getUserByUsername(String username);

	List<User> getAllUsers();

	List<User> findAllByProperty(Property property);

	Page<User> getUsersPaginated(Pageable pageable, List<User> users);

	// Update
	boolean updateUser(User user);

	// Delete
	void deleteUserById(long id);

}
