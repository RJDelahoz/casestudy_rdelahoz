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
			fetch = FetchType.LAZY)
	@JoinTable(name = "post_property")
	private Set<Post> posts = new LinkedHashSet<>();

	public Property(String address, String city, String state, String zipcode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	public Property(String address, String city, String state, String zipcode, Set<Post> posts) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.posts = posts;
	}

	public Property() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
