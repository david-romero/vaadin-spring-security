package com.app.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.model.types.Persona;

@Service
@Transactional
/**
 * 
 * @author David Romero Alcaide
 *
 */
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	/**
	 * 
	 */
	private UserAccountRepository userAccountRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	/**
	 * 
	 * Constructor
	 */
	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	/**
	 * 
	 * @author David Romero Alcaide
	 * @param persona
	 * @return
	 */
	public UserAccount findByPersona(Persona persona) {
		Assert.notNull(persona);

		UserAccount result;

		result = userAccountRepository.findByPersonaId(persona.getId());

		return result;
	}

	/**
	 * @author David Romero Alcaide
	 * @param userAccount
	 */
	public void save(UserAccount userAccount) {
		Assert.notNull(userAccount);
		UserAccount result = userAccountRepository.save(userAccount);
		Assert.notNull(result);
		Assert.isTrue(result.getId() > 0);
	}

	// Other business methods -------------------------------------------------

}