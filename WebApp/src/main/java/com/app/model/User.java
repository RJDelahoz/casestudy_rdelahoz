package com.app.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Credential credential;

	// Regex for email validation
	@Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}",
			message = "Please enter a valid email.")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@NotBlank
	@Size(min = 2, max = 25)
	@Column(name = "fName", length = 25, nullable = false)
	private String fName;

	@NotBlank
	@Size(min = 2, max = 25)
	@Column(name = "lName", length = 25, nullable = false)
	private String lName;

	@ManyToOne
	@JoinColumn(name = "property")
	private Property property;

	@OneToOne(mappedBy = "managedBy", cascade = CascadeType.ALL)
	private Organization organization;

	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "createdBy")
	private Set<Ticket> tickets = new HashSet<>();

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
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

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", fName='" + fName + '\'' +
				", lName='" + lName + '\'' +
				'}';
	}
}
