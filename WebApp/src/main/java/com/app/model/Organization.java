package com.app.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organization")
public class Organization {

	@Id
	@Column(name = "name")
	private String name;

	@OneToOne(mappedBy = "organization")
	private Credential credential;

	@OneToMany(targetEntity = Property.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JoinTable(name = "organization_property")
	private Set<Property> properties = new HashSet<>();

	public Organization() {
	}

	public Organization(String name, Credential credential, Set<Property> properties) {
		this.name = name;
		this.credential = credential;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}
}
