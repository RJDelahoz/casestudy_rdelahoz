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

	@OneToOne
	private User managedBy;

	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "managedBy")
	private Set<Property> properties = new HashSet<>();

	public Organization() {
	}

	public Organization(String name, User managedBy) {
		this.name = name;
		this.managedBy = managedBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getManagedBy() {
		return managedBy;
	}

	public void setManagedBy(User managedBy) {
		this.managedBy = managedBy;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}
}
