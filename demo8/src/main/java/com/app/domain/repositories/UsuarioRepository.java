/**
 * AdministradorRepository.java
 * demo8
 * 10/02/2014 19:46:33
 * Copyright David Romero Alcaide
 * com.app.domain.repositories
 */
package com.app.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.domain.model.types.Usuario;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface UsuarioRepository extends
		JpaRepository<Usuario, Integer> {

	/**
	 * @author David Romero Alcaide
	 * @param id
	 * @return
	 */
	@Query("select u from Usuario u where u.userAccount.id = ?1")
	Usuario findByUserAccountId(int id);

}
