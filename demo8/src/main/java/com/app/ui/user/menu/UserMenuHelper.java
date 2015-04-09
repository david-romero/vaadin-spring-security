/**
* UserMenuHelper.java
* appEducacionalVaadin
* 22/12/2014 22:30:04
* Copyright David
* com.app.ui.user
*/
package com.app.ui.user.menu;

import java.util.Arrays;
import java.util.List;

import com.app.infrastructure.security.Authority;
import com.app.ui.user.admin.view.banear.usuarios.BanearUsuarioView;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

/**
 * @author David
 *
 */
public enum UserMenuHelper {

	
	
	PANELCONTROL("PaneldeControl", PanelControlView.class, FontAwesome.HOME, true,Arrays.asList(new Authority(Authority.ADMINISTRADOR),new Authority(Authority.USUARIO))),
	BANEAR("BanearUsuario", BanearUsuarioView.class, FontAwesome.BAN, true,Arrays.asList(new Authority(Authority.ADMINISTRADOR)));
	

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private List<com.app.infrastructure.security.Authority> roles;

    private UserMenuHelper(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful,List<com.app.infrastructure.security.Authority> roles) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.roles = roles;
    }

    /**
	 * @return roles
	 */
	public List<com.app.infrastructure.security.Authority> getRoles() {
		return roles;
	}



	public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static UserMenuHelper getByViewName(final String viewName) {
    	UserMenuHelper result = null;
        for (UserMenuHelper viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
	
}
