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
import com.app.domain.model.types.Usuario;
import com.app.domain.repositories.UsuarioRepository;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.UserAccount;
import com.google.common.collect.Lists;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class UsuarioService {


	@Autowired
	/**
	 * 
	 */
	private UsuarioRepository usuarioRepository;


	/**
	 * Constructor
	 */
	public UsuarioService() {
		super();

	}

	// Métodos CRUD
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Usuario create() {
		Usuario user = new Usuario();
		user.setIdentidadConfirmada(false);
		UserAccount account = new UserAccount();
		List<Authority> authorities = Lists.newArrayList();
		Authority auth = new Authority();
		auth.setAuthority(Authority.USUARIO);
		authorities.add(auth);
		account.setAuthorities(authorities);
		user.setUserAccount(account);
		return user;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	// Other Business methods


	public Usuario findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Usuario result;

		result = usuarioRepository.findByUserAccountId(userAccount
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
	public void save(Usuario user) {
		Assert.notNull(user);
		Assert.notNull(user.getDNI());
		Assert.isTrue(Valida.validaDNI(user.getDNI()));
		Assert.isTrue(!user.getNombre().isEmpty());
		Assert.isTrue(!user.getApellidos().isEmpty());
		Assert.isTrue(!user.getEmail().isEmpty());
		Assert.notNull(user.getUserAccount());
		Assert.notNull(user.getUserAccount().getAuthorities());
		Assert.notEmpty(user.getUserAccount().getAuthorities());
		usuarioRepository.save(user);
	}


}
