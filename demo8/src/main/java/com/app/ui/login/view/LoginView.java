/**
 * LoginView.java
 * appEducacionalVaadin
 * 29/11/2014 14:56:01
 * Copyright David
 * com.app.ui
 */
package com.app.ui.login.view;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.UserAccount;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@SpringView(name=LoginView.NAME)
/**
 * @author David
 *
 */
public class LoginView extends VerticalLayout implements View, ClickListener {

	public static final String NAME = "login";
	
	@Autowired
	private AuthManager authManager;

	
	protected LoginForm loginForm;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4892607583348353123L;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}	
	
	@PostConstruct
    void init() {
		setSizeFull();
		loginForm = new LoginForm(this);
		setStyleName("loginview");
		addComponent(loginForm);

		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		
		addStyleName("v-align-center");
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			String username = loginForm.getTxtLogin().getValue();
			String password = loginForm.getTxtPassword().getValue();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(password, null);

			Authentication auth = new UsernamePasswordAuthenticationToken(
					username, hash);
			Authentication result = authManager.authenticate(auth);
			//Remember Me
			Boolean rememberMe = loginForm.getRememberMe().getValue();
			if (rememberMe){
				HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest() ;
				HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();
				authManager.rememberMe(result,req,resp);
			}
			SecurityContextHolder.getContext().setAuthentication(result);
			saveDataToSession(result);
			manageNavigation(result);
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Authentication failed: " + e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			Notification.show("Authentication failed: " + e.getMessage());
		}
	}
	
	/**
	 * @author Administrador
	 * @param result
	 */
	private void manageNavigation(Authentication result) {
		Collection<? extends GrantedAuthority> authorities = result
				.getAuthorities();
		GrantedAuthority grantedAuthority = Lists.newArrayList(authorities)
				.get(0);
		String authority = grantedAuthority.getAuthority();
		if (authority.equals("ROLE_ANONYMOUS")) {
			goToLoginView();
		}
		goToView(authority);
	}

	/**
	 * @author David
	 * @param result
	 * @throws Throwable
	 */
	private void saveDataToSession(Authentication result) throws Throwable {
		UserAccount account = (UserAccount) result.getPrincipal();

		if (account != null) {
			VaadinSession.getCurrent().setAttribute(UserAccount.class, account);
		} else {
			throw new Throwable();
		}
	}
	
	/**
	 * @author David
	 * @param administrador
	 */
	private void goToView(String view) {
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo(view);
	}

	/**
	 * @author David
	 */
	private void goToLoginView() {
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo("login");
	}

}
