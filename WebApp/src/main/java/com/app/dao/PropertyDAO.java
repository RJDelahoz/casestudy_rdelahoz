package com.app.dao;

import com.app.model.Organization;
import com.app.model.Property;

import java.util.List;

public interface PropertyDAO {

	// Create
	void addProperty(Property property);

	// Retrieve
	Property getPropertyById(long id);

	Property getPropertyByAddress(String address);

	List<Property> getPropertyOrganization(Organization organization);

	// Update
	void updateProperty(Property property);

	// Delete
	void deleteProperty(Property property);
}
