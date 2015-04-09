/**
 * RememberMeToken.java
 * demo8
 * 9/4/2015 9:44:46
 * Copyright Administrador
 * com.app.infrastructure.security
 */
package com.app.infrastructure.security;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import com.app.domain.model.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David Romero Alcaide
 *
 */
public class RememberMeToken extends DomainEntity {

	private String username;

	private String series;

	private String tokenValue;

	private Date date;

	public RememberMeToken() {

	}

	public RememberMeToken(PersistentRememberMeToken token) {
		this.series = token.getSeries();
		this.username = token.getUsername();
		this.tokenValue = token.getTokenValue();
		this.date = token.getDate();
	}

	@NotBlank
	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 * Establecer el username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	/**
	 * @return series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 * Establecer el series
	 */
	public void setSeries(String series) {
		this.series = series;
	}
	@NotBlank
	/**
	 * @return tokenValue
	 */
	public String getTokenValue() {
		return tokenValue;
	}

	/**
	 * @param tokenValue the tokenValue to set
	 * Establecer el tokenValue
	 */
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	/**
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 * Establecer el date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
