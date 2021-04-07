package com.app.repo;

import com.app.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

	Optional<Organization> findByManagedBy_Credential_Username(String username);
}
