/**
 * AdministradorRepository.java
 * appEducacional
 * 10/02/2014 19:46:33
 * Copyright David Romero Alcaide
 * com.app.domainLayer.repositories
 */
package com.app.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.domain.model.types.Administrador;

@Repository
/**
 * @author David Romero Alcaide
 *
 */
public interface AdministradorRepository extends
		JpaRepository<Administrador, Integer> {

	/**
	 * @author David Romero Alcaide
	 * @param id
	 * @return
	 */
	@Query("select p from Administrador p where p.userAccount.id = ?1")
	Administrador findByUserAccountId(int id);

}
