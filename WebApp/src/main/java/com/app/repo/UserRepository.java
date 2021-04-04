package com.app.repo;



import com.app.model.Credential;
import com.app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User getUserByEmail(String email);
}
