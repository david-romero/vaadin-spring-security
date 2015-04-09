/**
 * AuthManager.java
 * appEducacionalVaadin
 * 29/11/2014 14:50:06
 * Copyright David
 * com.app.infrastructure.security
 */
package com.app.infrastructure.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.app.domain.repositories.RememberMeTokenRepository;
import com.vaadin.server.VaadinSession;

@Component
@Transactional
/**
 * @author David
 *
 */
public class AuthManager implements AuthenticationManager, Serializable,PersistentTokenRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6870121373591461123L;
	
	private static final Logger log = Logger.getLogger(AuthManager.class);
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private RememberMeAuthenticationProvider provicer;
	
	@Autowired
	private RememberMeTokenRepository rememberMeTokenRepository;
	@Autowired
	private JpaTransactionManager jpaTransactionManager;
	
	@Autowired
	/**
	 * Manager
	 */
	private PlatformTransactionManager transactionManager;
	/**
	 * State of transaction
	 */
	protected TransactionStatus txStatus;

	@Autowired
	private RememberMeServices rememberMeService;

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String username = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		UserDetails user = loginService.loadUserByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			Collection<? extends GrantedAuthority> authorities = user
					.getAuthorities();
			Credentials credentials = new Credentials();
			credentials.setJ_username(username);
			credentials.setPassword(password);
			return new UsernamePasswordAuthenticationToken(user, password,
					authorities);
		}
		throw new BadCredentialsException("Bad Credentials");
	}

	public void rememberMe(Authentication auth,HttpServletRequest request,HttpServletResponse response) {
		rememberMeService.loginSuccess(request, response,
				auth);
	}

	public Authentication autoLoginRememberMe(HttpServletRequest request,HttpServletResponse response) {
		Authentication auth = null;
		try {
			beginTransaction(false);
			auth = rememberMeService.autoLogin(request,response);
			commitTransaction();
		} catch (CookieTheftException securityException) {
			rollbackTransaction();
			log.error("Error de seguridad con la cookie y remember-me " + securityException.getLocalizedMessage());
			//TODO - Remove rememberme in database for username
		}catch (Exception generalException) {
			rollbackTransaction();
			log.error("Error de la cookie y remember-me " + generalException.getLocalizedMessage());
		}
		if (auth != null){
			saveDataToSession(auth);
		}
		return auth;
	}
	
	public void createNewToken(PersistentRememberMeToken token) {
		RememberMeToken newToken = new RememberMeToken(token);
		this.rememberMeTokenRepository.save(newToken);
	}

	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = this.rememberMeTokenRepository
				.findBySeries(series);
		if (token != null) {
			token.setTokenValue(tokenValue);
			token.setDate(lastUsed);
			this.rememberMeTokenRepository.save(token);
		}

	}

	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		RememberMeToken token = this.rememberMeTokenRepository
				.findBySeries(seriesId);
		return new PersistentRememberMeToken(token.getUsername(),
				token.getSeries(), token.getTokenValue(), token.getDate());
	}

	public void removeUserTokens(String username) {
		beginTransaction(true);
		List<RememberMeToken> tokens = this.rememberMeTokenRepository
				.findByUsername(username);
		commitTransaction();
		beginTransaction(false);
		this.rememberMeTokenRepository.delete(tokens);
		commitTransaction();
	}

	/**
	 * @author David
	 * @param result
	 * @throws Throwable
	 */
	private void saveDataToSession(Authentication result){
		UserAccount account = (UserAccount) result.getPrincipal();
		if (account != null) {
			VaadinSession.getCurrent().setAttribute(UserAccount.class, account);
		} 
	}
	
	/**
	 * Begin a transaction with the database
	 * 
	 * @author David Romero Alcaide
	 * @param readOnly
	 */
	protected void beginTransaction(boolean readOnly) {
		assert txStatus == null;

		DefaultTransactionDefinition definition;

		definition = new DefaultTransactionDefinition();
		definition
				.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_DEFAULT);
		definition
				.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
		definition.setReadOnly(readOnly);
		txStatus = transactionManager.getTransaction(definition);
	}
	
	/**
	 * Commit a transaction
	 * 
	 * @author David Romero Alcaide
	 * @throws Throwable
	 */
	protected void commitTransaction() throws TransactionException {
		assert txStatus != null;

		try {
			transactionManager.commit(txStatus);
			txStatus = null;
		} catch (TransactionException oops) {
			throw oops;
		}
	}

	/**
	 * Rollback a transaction
	 * 
	 * @author David Romero Alcaide
	 * @throws Throwable
	 */
	protected void rollbackTransaction() throws TransactionException {
		assert txStatus != null;
		try {
			if (!txStatus.isCompleted()) {
				transactionManager.rollback(txStatus);
			}
			txStatus = null;
		} catch (TransactionException oops) {
			throw oops;
		}
	}
}
