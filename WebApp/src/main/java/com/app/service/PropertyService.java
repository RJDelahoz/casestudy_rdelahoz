package com.app.service;

import com.app.dao.PropertyDAO;
import com.app.model.Organization;
import com.app.model.Property;
import com.app.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService implements PropertyDAO {

	private final PropertyRepository propertyRepository;

	@Autowired
	public PropertyService(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}


	@Override
	public void addProperty(Property property) {
		propertyRepository.save(property);
	}

	@Override
	public Property getPropertyById(long id) {
		Optional<Property> optionalProperty = propertyRepository.findById(id);
		return optionalProperty.orElse(null);
	}

	@Override
	public Property getPropertyByAddress(String address) {
		return null;
	}

	@Override
	public List<Property> getPropertyOrganization(Organization organization) {
		return null;
	}

	@Override
	public void updateProperty(Property property) {
		Optional<Property> optionalProperty = propertyRepository.findById(property.getId());
		if (optionalProperty.isPresent())
			propertyRepository.save(property);
	}

	@Override
	public void deleteProperty(Property property) {
		propertyRepository.delete(property);
	}

	public Optional<Property> findPropertyById(long id) {
		return propertyRepository.findById(id);
	}

	public Optional<Property> findPropertyByAddressAndOrgName(String address, String orgName) {
		return propertyRepository.findPropertyByAddressAndManagedBy_Name(address, orgName);
	}
}
