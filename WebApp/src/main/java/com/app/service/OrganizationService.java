package com.app.service;

import com.app.dao.OrganizationDao;
import com.app.model.Organization;
import com.app.repo.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrganizationService implements OrganizationDao {

	private final OrganizationRepository organizationRepository;

	@Autowired
	public OrganizationService(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public void addOrganization(Organization organization) {
		organizationRepository.save(organization);
	}

	@Override
	public Optional<Organization> findOrganizationByName(String name) {
		return organizationRepository.findById(name);
	}

	@Override
	public Optional<Organization> findByManagerUserName(String username) {
		return organizationRepository.findByManagedBy_Credential_Username(username);
	}

	// To be used by admin
	@Override
	public boolean updateOrganizationManager(Organization organization) {
		Optional<Organization> optionalOrganization =
				findOrganizationByName(organization.getName());
		if (optionalOrganization.isPresent()) {
			organizationRepository.save(organization);
			return true;
		}
		return false;
	}

	@Override
	public void deleteOrganizationByName(String name) {
		organizationRepository.deleteById(name);
	}
}
