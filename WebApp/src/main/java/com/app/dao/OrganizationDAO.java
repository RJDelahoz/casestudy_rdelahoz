package com.app.dao;

import com.app.model.Organization;
import com.app.model.Property;

import java.util.List;

public interface OrganizationDAO {

	// Create
	void addOrganization(Organization organization);

	// Retrieve
	Organization getOrganizationByName(String name);

	// Update
	void updateOrganization(String name);

	// Delete
	Organization deleteOrganization(Organization organization);

	void deleteOrganizationByName(String name);
}
