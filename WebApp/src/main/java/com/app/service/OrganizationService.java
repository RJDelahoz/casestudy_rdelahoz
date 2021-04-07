package com.app.service;

import com.app.model.Organization;
import com.app.repo.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

	private final OrganizationRepository organizationRepository;

	@Autowired
	public OrganizationService(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	public void addOrganization(Organization organization) {
		organizationRepository.save(organization);
	}

	public Optional<Organization> findOrganizationByName(String name) {
		return organizationRepository.findById(name);
	}

	public Optional<Organization> findByManagerUserName(String username) {
		return organizationRepository.findByManagedBy_Credential_Username(username);
	}
}
