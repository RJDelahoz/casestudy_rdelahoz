package com.app.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "fName", length = 25, nullable = false)
	private String fName;

	@Column(name = "lName", length = 25, nullable = false)
	private String lName;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Credential credential;

	@OneToOne(mappedBy = "managedBy", cascade = CascadeType.ALL)
	private Organization organization;

	@OneToMany(targetEntity = Property.class,
			fetch = FetchType.EAGER)
	@JoinTable(name = "user_property")
	private Set<Property> properties = new HashSet<>();

	@OneToMany(mappedBy = "user",
			orphanRemoval = true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Ticket> tickets = new LinkedHashSet<>();

	public User() {
	}

	public User(String email, String fName, String lName, Credential credential, Organization organization,
				Set<Property> properties, Set<Ticket> tickets) {
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.credential = credential;
		this.organization = organization;
		this.properties = properties;
		this.tickets = tickets;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
}
