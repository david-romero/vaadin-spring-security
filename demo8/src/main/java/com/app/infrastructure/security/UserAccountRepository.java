/* UserAccountRepository.java
 * 
 */

package com.app.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends
		JpaRepository<UserAccount, Integer> {

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findByUsername(String username);

	@Query("select a.userAccount from Persona a where a.id = ?1")
	UserAccount findByPersonaId(int personaId);

}
