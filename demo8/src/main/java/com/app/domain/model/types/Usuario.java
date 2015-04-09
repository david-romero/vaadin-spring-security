/**
 * Usuario.java
 * 10/02/2014 19:44:29
 * Copyright David Romero Alcaide
 * com.app.domain.model.types
 */
package com.app.domain.model.types;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David Romero Alcaide
 *
 */
public class Usuario extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2584127127405605820L;

	/**
	 * Constructor
	 */
	public Usuario() {
		super();

	}

}
