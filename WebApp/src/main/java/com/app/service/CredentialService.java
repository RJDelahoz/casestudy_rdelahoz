package com.app.service;

import com.app.model.Authority;
import com.app.model.Credential;
import com.app.repo.CredentialRepository;
import com.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.Optional;
import java.util.Set;

@Service
public class CredentialService implements UserDetailsService {

	private final CredentialRepository credentialRepository;
	private final UserRepository userRepository;

	@Autowired
	public CredentialService(CredentialRepository credentialRepository, UserRepository userRepository) {
		this.credentialRepository = credentialRepository;
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.printf("%n%nLoading user with username: %s%n%n", username);

		// User entity
		Optional<Credential> optionalCredential = credentialRepository.findByUsername(username);

		UserBuilder userBuilder;

		if(optionalCredential.isPresent()) {
			Credential credential = optionalCredential.get();
			userBuilder = User.withUsername(username);
			userBuilder.password(credential.getPassword());
			userBuilder.disabled(!credential.isEnabled());

			Set<Authority> authorities = credential.getAuthorities();
 			String[] permissions = authorities.stream()
					.map(Authority::getRole).toArray(String[]::new);

 			userBuilder.authorities(permissions);
		} else{
			throw new UsernameNotFoundException("User not found!");
		}

		return userBuilder.build();
	}

	// Create
	public void addCredential(Credential credential) {
		credentialRepository.save(credential);
	}

	// Retrieve
	public Optional<Credential> getCredentialByUsername(String username){
		return credentialRepository.findByUsername(username);
	}
}
