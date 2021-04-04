package com.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(value = AuthorityId.class)
@Table(name = "authority")
public class Authority {

	@Id
	@Column(name = "role")
	private String role;

	@Id
	@ManyToOne
	@JoinColumn(name = "credential")
	private Credential credential;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Authority)) return false;

		Authority authority = (Authority) o;

		if (!Objects.equals(role, authority.role)) return false;
		return Objects.equals(credential, authority.credential);
	}

	@Override
	public int hashCode() {
		int result = role != null ? role.hashCode() : 0;
		result = 31 * result + (credential != null ? credential.hashCode() : 0);
		return result;
	}
}
class AuthorityId implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@Column(name = "role")
	private String role;

	@Id
	@ManyToOne(targetEntity = Credential.class)
	@JoinColumn(name = "credential")
	private Credential credential;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AuthorityId)) return false;

		AuthorityId that = (AuthorityId) o;

		if (!Objects.equals(role, that.role)) return false;
		return Objects.equals(credential, that.credential);
	}

	@Override
	public int hashCode() {
		int result = role != null ? role.hashCode() : 0;
		result = 31 * result + (credential != null ? credential.hashCode() : 0);
		return result;
	}
}
