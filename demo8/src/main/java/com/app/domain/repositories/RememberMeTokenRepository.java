/**
* RememberMeTokenRepository.java
* demo8
* 9/4/2015 9:57:32
* Copyright Administrador
* com.app.domain.repositories
*/
package com.app.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.infrastructure.security.RememberMeToken;

@Repository
/**
 * @author Administrador
 *
 */
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Integer>{
	
	@Query("select r from RememberMeToken r where r.series = ?1")
	RememberMeToken findBySeries(String series);
	
	@Query("select r from RememberMeToken r where r.username = ?1")
    List<RememberMeToken> findByUsername(String username);
	
}
