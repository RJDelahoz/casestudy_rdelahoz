package com.app.dao;

import com.app.model.Property;

public interface PropertyDAO {

	// Create
	void addProperty(Property property);

	// Retrieve
	Property getPropertyById(long id);

	// Update
	void updateProperty(Property property);

	// Delete
	Property deletePropertyById(long id);
}
