package com.app.repo;



import com.app.model.Authority;
import com.app.model.Property;
import com.app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByCredential_Username(String username);

	List<User> getAllByCredentialAuthoritiesNotContaining(Authority authority);

	List<User> findAllByProperty(Property property);
}
