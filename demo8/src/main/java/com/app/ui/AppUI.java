/**
 * AppUi.java
 * appEducacionalVaadin
 * 29/11/2014 14:39:22
 * Copyright David
 * com.app.ui
 */
package com.app.ui;

import java.util.Collection;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.web.context.ContextLoaderListener;

import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.LoginService;
import com.app.infrastructure.security.UserAccount;
import com.app.presenter.event.EventComunicationBus;
import com.app.ui.login.view.LoginView;
import com.app.ui.user.admin.view.AdminView;
import com.app.ui.user.usuario.view.UsuarioView;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("appeducacionalvaadin")
/**
 * @author David
 *
 */
public class AppUI extends UI implements ErrorHandler {
	
	private static final String applicationKey = "demo8"; 
	
	/**
     * The id of this UI, used to find the server side instance of the UI form
     * which a request originates. A negative value indicates that the UI id has
     * not yet been assigned by the Application.
     * 
     * @see VaadinSession#getNextUIid()
     */
    private int uiId = -1;
    
    private String embedId;
	
	@WebServlet(value = "/*", asyncSupported = true)
	public static class Servlet extends SpringVaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8438556552964111310L;
		
	}

	@WebListener
	public static class CustomLoaderListener extends ContextLoaderListener {
	}

	@Configuration
	@EnableVaadin
	public static class CustomConfiguration {

		@Autowired
		private LoginService loginService;
		
		@Autowired
		private AuthManager authManager;
		

		@Bean
		public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
			RememberMeAuthenticationProvider rememberMeAuthenticationProvider = new RememberMeAuthenticationProvider(
					applicationKey);
			return rememberMeAuthenticationProvider;
		}

		@Bean
		public RememberMeServices rememberMeServices() {
			PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
					applicationKey, loginService,
					authManager);
			rememberMeServices.setAlwaysRemember(true);
			return rememberMeServices;
		}


	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2252711826491740298L;

	@Autowired
	SpringViewProvider springViewProvider;
	
	@Autowired
	AuthManager authManager;

	/**
	 * 
	 */
	@Autowired
	private transient ApplicationContext applicationContext;

	private final EventComunicationBus eventComunicationBus = new EventComunicationBus();

	
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.UI#doInit(com.vaadin.server.VaadinRequest, int, java.lang.String)
	 */
	@Override
	public void doInit(VaadinRequest request, int uiId, String embedId) {
		if (this.uiId != -1) {
            String message = "This UI instance is already initialized (as UI id "
                    + this.uiId
                    + ") and can therefore not be initialized again (as UI id "
                    + uiId + "). ";

            if (getSession() != null
                    && !getSession().equals(VaadinSession.getCurrent())) {
                message += "Furthermore, it is already attached to another VaadinSession. ";
            }
            message += "Please make sure you are not accidentally reusing an old UI instance.";

            throw new IllegalStateException(message);
        }
        this.uiId = uiId;
        this.embedId = embedId;

        // Actual theme - used for finding CustomLayout templates
        setTheme(request.getParameter("theme"));

        getPage().init(request);

        // Call the init overridden by the application developer
        init(request);
	}
	
	

	/**
     * Gets the id of the UI, used to identify this UI within its application
     * when processing requests. The UI id should be present in every request to
     * the server that originates from this UI.
     * {@link VaadinService#findUI(VaadinRequest)} uses this id to find the
     * route to which the request belongs.
     * <p>
     * This method is not intended to be overridden. If it is overridden, care
     * should be taken since this method might be called in situations where
     * {@link UI#getCurrent()} does not return this UI.
     * 
     * @return the id of this UI
     */
    public int getUIId() {
        return uiId;
    }
    
    /**
     * Gets a string the uniquely distinguishes this UI instance based on where
     * it is embedded. The embed identifier is based on the
     * <code>window.name</code> DOM attribute of the browser window where the UI
     * is displayed and the id of the div element where the UI is embedded.
     * 
     * @since 7.2
     * @return the embed id for this UI, or <code>null</code> if no id known
     */
    public String getEmbedId() {
        return embedId;
    }



	@Override
	protected void init(VaadinRequest request) {
		HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest() ;
		HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();
		VaadinSession.getCurrent().setErrorHandler(this);
		Responsive.makeResponsive(this);
		EventComunicationBus.register(this);
		NavigatorUI navigator = new NavigatorUI(this, this);

		navigator.addProvider(springViewProvider);

		navigator.addViewChangeListener(new ViewChangeListenerUI());
		navigator.addView("login", LoginView.class);

		navigator.addView(Authority.ADMINISTRADOR, AdminView.class);

		navigator.addView(Authority.USUARIO, UsuarioView.class);

		setNavigator(navigator);
		Authentication authRememberMe = null;
		if ( req != null && resp != null ){
			authRememberMe = authManager.autoLoginRememberMe(req,resp);
		}
		
		if ( authRememberMe == null ){
			navigator.navigateTo("login");
		}else{
			SecurityContextHolder.getContext().setAuthentication(authRememberMe);
			if (authRememberMe != null && authRememberMe.isAuthenticated()) {
				Collection<? extends GrantedAuthority> authorities = authRememberMe
						.getAuthorities();
				GrantedAuthority grantedAuthority = Lists.newArrayList(authorities)
						.get(0);
				String authority = grantedAuthority.getAuthority();
				navigator.navigateTo(authority);
			}
		}
		

	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public SpringViewProvider getSpringViewProvider() {
		return springViewProvider;
	}

	public static EventComunicationBus getEventComunicationBus() {
		return ((AppUI) getCurrent()).eventComunicationBus;
	}

	public static String getAuthority() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String authority = "";
		if (authentication != null && authentication.isAuthenticated()) {
			Collection<? extends GrantedAuthority> authorities = authentication
					.getAuthorities();
			GrantedAuthority grantedAuthority = Lists.newArrayList(authorities)
					.get(0);
			authority = grantedAuthority.getAuthority();
		}
		return authority;
	}

	public static UserAccount getCurrentUser() {
		return (UserAccount) VaadinSession.getCurrent().getAttribute(
				UserAccount.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.server.ErrorHandler#error(com.vaadin.server.ErrorEvent)
	 */
	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		// connector event
		if (event.getThrowable().getCause() instanceof AccessDeniedException) {
			AccessDeniedException accessDeniedException = (AccessDeniedException) event
					.getThrowable().getCause();
			Notification.show(accessDeniedException.getMessage(),
					Notification.Type.ERROR_MESSAGE);

			// Cleanup view. Now Vaadin ignores errors and always shows the
			// view. :-(
			// since beta10
			setContent(null);
			return;
		}

		// Error on page load. Now it doesn't work. User sees standard error
		// page.
		if (event.getThrowable() instanceof AccessDeniedException) {
			AccessDeniedException exception = (AccessDeniedException) event
					.getThrowable();

			Label label = new Label(exception.getMessage());
			label.setWidth(-1, Unit.PERCENTAGE);

			Link goToMain = new Link("Go to main", new ExternalResource("/"));

			VerticalLayout layout = new VerticalLayout();
			layout.addComponent(label);
			layout.addComponent(goToMain);
			layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			layout.setComponentAlignment(goToMain, Alignment.MIDDLE_CENTER);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponent(layout);
			mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

			setContent(mainLayout);
			Notification.show(exception.getMessage(),
					Notification.Type.ERROR_MESSAGE);
			return;
		}

		DefaultErrorHandler.doDefault(event);
	}
}
