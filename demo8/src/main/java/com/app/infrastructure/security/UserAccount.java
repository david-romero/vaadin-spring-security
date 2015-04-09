package com.app.infrastructure.security;

/**
 * 
 */
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.app.domain.model.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class UserAccount extends DomainEntity implements UserDetails {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 */
	private static final long serialVersionUID = 7254823034213841482L;

	/**
	 * 
	 * Constructor
	 */
	public UserAccount() {
		super();
		this.authorities = new ArrayList<Authority>();
	}

	// Attributes -------------------------------------------------------------

	// UserDetails interface --------------------------------------------------
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private Collection<Authority> authorities;

	@Size(min = 5, max = 32)
	@Column(unique = true)
	/**
	 * 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	/**
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty
	@Valid
	@ElementCollection
	// @Cascade(value = { CascadeType.ALL })
	/**
	 * 
	 */
	public Collection<Authority> getAuthorities() {
		// WARNING: Should return an unmodifiable copy, but it's not possible
		// with hibernate!
		return authorities;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param authorities
	 */
	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param authority
	 */
	public void addAuthority(Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(!authorities.contains(authority));
		authorities.add(authority);
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param authority
	 */
	public void removeAuthority(Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(authorities.contains(authority));

		authorities.remove(authority);
	}

	@Transient
	/**
	 * 
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	/**
	 * 
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	/**
	 * 
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	/**
	 * 
	 */
	public boolean isEnabled() {
		return true;
	}

}
