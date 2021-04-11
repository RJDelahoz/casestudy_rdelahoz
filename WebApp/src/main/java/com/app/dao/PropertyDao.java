package com.app.dao;

import com.app.model.Organization;
import com.app.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyDao {
	void addProperty(Property property);

	Property getPropertyById(long id);

	Property getPropertyByAddress(String address);

	List<Property> getPropertyOrganization(Organization organization);

	Optional<Property> findPropertyById(long id);

	Optional<Property> findPropertyByAddressAndOrgName(String address, String orgName);

	void updateProperty(Property property);

	void deleteProperty(Property property);
}
