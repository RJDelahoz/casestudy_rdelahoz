package com.app.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "property")
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "address", nullable = false, unique = true)
	private String address;

	@Pattern(regexp = "[\\w\\s]+",
			message = "Please enter a valid city.")
	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false, length = 2)
	private String state;

	@Pattern(regexp = "^[0-9]{5}", message = "only 5 digits")
	@Column(name = "zipcode", nullable = false, length = 5)
	private String zipcode;

	@OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Memo memo;

	@ManyToOne
	@JoinColumn(name = "managedBy")
	private Organization managedBy;

	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "property")
	private Set<User> users = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "property")
	private Set<Ticket> tickets = new HashSet<>();

	public Property() {
	}

	public Property(String address, String city, String state, String zipcode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
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

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Organization getManagedBy() {
		return managedBy;
	}

	public void setManagedBy(Organization managedBy) {
		this.managedBy = managedBy;
	}

}
