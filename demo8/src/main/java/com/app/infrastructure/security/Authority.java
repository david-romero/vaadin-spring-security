/* Authority.java
 * 
 */

package com.app.infrastructure.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class Authority implements GrantedAuthority {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Constructor
	 */
	public Authority() {
		super();
	}
	
	/**
	 * 
	 * Constructor
	 */
	public Authority(String authority) {
		super();
		this.authority = authority;
	}

	// Values -----------------------------------------------------------------
	/**
	 * 
	 */
	public static final String USUARIO = "USUARIO";

	/**
	 * 
	 */
	public static final String ADMINISTRADOR = "ADMINISTRADOR";

	// Attributes -------------------------------------------------------------

	/**
	 * 
	 */
	private String authority;

	@NotBlank
	@Pattern(regexp = "^"  + USUARIO + "|" + ADMINISTRADOR + "$")
	/**
	 * 
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(USUARIO);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(ADMINISTRADOR);
		result.add(authority);

		return result;
	}

	// Equality ---------------------------------------------------------------

	@Override
	/**
	 * 
	 */
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	/**
	 * 
	 */
	public boolean equals(Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other)
					.getAuthority()));

		return result;
	}

}
