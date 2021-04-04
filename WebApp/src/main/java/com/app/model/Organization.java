package com.app.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organization")
public class Organization {

	@Id
	@Column(name = "name")
	private String name;

	@OneToMany(targetEntity = Property.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JoinTable(name = "organization_property")
	private Set<Property> properties = new HashSet<>();

	@OneToMany(targetEntity = User.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	@JoinTable(name = "organization_user")
	private Set<User> users = new HashSet<>();

	public Organization() {
	}

	public Organization(String name, Set<Property> properties, Set<User> users) {
		this.name = name;
		this.properties = properties;
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
