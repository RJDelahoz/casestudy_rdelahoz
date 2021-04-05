package com.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "property")
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false, length = 2)
	private String state;

	@Column(name = "zipcode", nullable = false, length = 5)
	private String zipcode;

	@OneToMany(targetEntity = Post.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	@JoinTable(name = "property_post")
	private Set<Post> posts = new LinkedHashSet<>();

	@OneToMany(targetEntity = Ticket.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JoinTable(name = "property_ticket")
	private Set<Ticket> tickets = new LinkedHashSet<>();

	public Property() {
	}

	public Property(String address, String city, String state, String zipcode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}


}
