package com.app.dao;

import com.app.model.Organization;

import java.util.Optional;

public interface OrganizationDao {

	// Create
	void addOrganization(Organization organization);

	// Retrieve
	Optional<Organization> findOrganizationByName(String name);

	Optional<Organization> findByManagerUserName(String username);

	// Update
	boolean updateOrganizationManager(Organization organization);

	// Delete
	void deleteOrganizationByName(String name);
}
