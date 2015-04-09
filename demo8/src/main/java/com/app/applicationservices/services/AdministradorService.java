/**
 * AdministradorService.java
 * appEducacional
 * 10/02/2014 19:46:01
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.domainservices.Valida;
import com.app.domain.model.types.Administrador;
import com.app.domain.repositories.AdministradorRepository;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.google.common.collect.Lists;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class AdministradorService{

	@Autowired
	/**
	 * 
	 */
	private AdministradorRepository administratorRepository;


	/**
	 * Constructor
	 */
	public AdministradorService() {
	}

	// Métodos CRUD
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Administrador create() {
		Administrador tutor = new Administrador();
		tutor.setIdentidadConfirmada(false);
		UserAccount account = new UserAccount();
		List<Authority> authorities = Lists.newArrayList();
		Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRADOR);
		authorities.add(auth);
		account.setAuthorities(authorities);
		tutor.setUserAccount(account);
		return tutor;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Administrador> findAll() {
		return administratorRepository.findAll();
	}

	// Other Business methods

	public Administrador findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrador result;

		result = administratorRepository.findByUserAccountId(userAccount
				.getId());
		
		Assert.notNull(result.getUserAccount());
		
		Assert.notEmpty(result.getUserAccount().getAuthorities());

		return result;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param admin
	 */
	public void save(Administrador admin) {
		Assert.notNull(admin);
		Assert.notNull(admin.getDNI());
		Assert.isTrue(Valida.validaDNI(admin.getDNI()));
		Assert.isTrue(!admin.getNombre().isEmpty());
		Assert.isTrue(!admin.getApellidos().isEmpty());
		Assert.isTrue(!admin.getEmail().isEmpty());
		Assert.notNull(admin.getUserAccount());
		Assert.notNull(admin.getUserAccount().getAuthorities());
		Assert.notEmpty(admin.getUserAccount().getAuthorities());
		administratorRepository.save(admin);
	}


}
