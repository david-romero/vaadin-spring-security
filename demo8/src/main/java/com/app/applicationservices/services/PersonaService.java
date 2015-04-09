/**
 * PersonaService.java
 * 10/02/2014 19:46:01
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.domainservices.Valida;
import com.app.domain.model.types.Persona;
import com.app.domain.repositories.PersonaRepository;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class PersonaService{


	@Autowired
	/**
	 * 
	 */
	private PersonaRepository personaRepository;


	/**
	 * Constructor
	 */
	public PersonaService() {
	}

	// Métodos CRUD

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Persona> findAll() {
		return personaRepository.findAll();
	}

	// Other Business methods



	/**
	 * 
	 * @author David Romero Alcaide
	 * @param persona
	 */
	public void save(Persona persona) {
		Assert.notNull(persona);
		Assert.notNull(persona.getDNI());
		Assert.isTrue(Valida.validaDNI(persona.getDNI()));
		Assert.isTrue(!persona.getNombre().isEmpty());
		Assert.isTrue(!persona.getApellidos().isEmpty());
		Assert.isTrue(!persona.getEmail().isEmpty());
		personaRepository.save(persona);
	}


}
