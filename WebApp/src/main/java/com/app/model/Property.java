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

	@Column(name = "memo", nullable = true)
	private String memo;

	@OneToMany(targetEntity = Ticket.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JoinTable(name = "property_ticket")
	private Set<Ticket> tickets = new LinkedHashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization")
	private Organization managedBy;

	public Property() {
	}

	public Property(String address, String city, String state, String zipcode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
