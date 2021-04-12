package com.app.repo;

import com.app.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

	Optional<Property> findPropertyByAddressAndManagedBy_Name(String address, String name);

	Optional<Property> findByAddress(String address);
}
