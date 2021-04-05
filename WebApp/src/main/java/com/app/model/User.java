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
	@Column(name = "id")
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

	@OneToOne
	@JoinColumn(name = "organization")
	private Organization organization;

	@OneToMany(mappedBy = "user",
			orphanRemoval = true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Post> posts = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user",
			orphanRemoval = true,
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Post> tickets = new LinkedHashSet<>();

	public User() {
	}

	public User(String email, String fName, String lName, Credential credential, Organization organization,
				Set<Post> posts, Set<Post> tickets) {
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.credential = credential;
		this.organization = organization;
		this.posts = posts;
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

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Post> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Post> tickets) {
		this.tickets = tickets;
	}
}
