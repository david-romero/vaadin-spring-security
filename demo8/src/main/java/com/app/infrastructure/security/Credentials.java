/** Credentials.java
 * 
 */
package com.app.infrastructure.security;

/**
 * 
 */
import javax.validation.constraints.Size;

/**
 * 
 * @author David Romero Alcaide
 * 
 */
public class Credentials {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 * Constructor
	 */
	public Credentials() {
		super();
	}

	// Attributes -------------------------------------------------------------
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;

	@Size(min = 5, max = 32)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param username
	 */
	public void setJ_username(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
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

}
