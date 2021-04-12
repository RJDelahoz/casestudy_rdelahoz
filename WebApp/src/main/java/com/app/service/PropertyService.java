package com.app.service;

import com.app.dao.PropertyDao;
import com.app.model.Organization;
import com.app.model.Property;
import com.app.model.Ticket;
import com.app.repo.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService implements PropertyDao {

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
	public Optional<Property> getPropertyByAddress(String address) {
		return propertyRepository.findByAddress(address);
	}

	@Override
	public List<Property> getPropertyOrganization(Organization organization) {
		return null;
	}

	@Override
	public Optional<Property> findPropertyById(long id) {
		return propertyRepository.findById(id);
	}

	@Override
	public Optional<Property> findPropertyByAddressAndOrgName(String address, String orgName) {
		return propertyRepository.findPropertyByAddressAndManagedBy_Name(address, orgName);
	}

	@Override
	public void updateProperty(Property property) {
		Optional<Property> optionalProperty = propertyRepository.findById(property.getId());
		if (optionalProperty.isPresent()) {
			propertyRepository.save(property);
		}
	}

	@Override
	public void deleteProperty(Property property) {
		propertyRepository.delete(property);
	}


}
