package com.app.repo;

import com.app.model.Credential;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

	Optional<Credential> findByUsername(String name);

	Optional<Credential> findByUser(User user);
}
