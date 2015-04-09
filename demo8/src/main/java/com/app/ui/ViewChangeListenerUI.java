/**
* ViewChangeListenerUI.java
* appEducacionalVaadin
* 7/12/2014 0:23:19
* Copyright David
* com.app.ui
*/
package com.app.ui;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.ui.user.UserView;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.navigator.ViewChangeListener;

/**
 * @author David
 *
 */
public class ViewChangeListenerUI implements ViewChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7815739887770672318L;

	/**
	 * Constructor
	 */
	public ViewChangeListenerUI() {
	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.ViewChangeListener#beforeViewChange(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		if (event.getNewView() instanceof UserView){
			return checkPermisions(event);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.ViewChangeListener#afterViewChange(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void afterViewChange(ViewChangeEvent event) {
	}
	
	/**
	 * @author David
	 * @param event 
	 */
	private boolean checkPermisions(ViewChangeEvent event) {
		if (event.getNewView() instanceof UserView){
			if (( (UserView) event.getNewView()).getRol() == null){
				return false;
			}
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				Collection<? extends GrantedAuthority> authorities = authentication
						.getAuthorities();
				GrantedAuthority grantedAuthority = Lists.newArrayList(authorities)
						.get(0);
				String authority = grantedAuthority.getAuthority();
				if (authority.equals("ROLE_ANONYMOUS")) {
					return false;
				}else if(!authority.equals(( (UserView) event.getNewView()).getRol().getAuthority())){
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

}
