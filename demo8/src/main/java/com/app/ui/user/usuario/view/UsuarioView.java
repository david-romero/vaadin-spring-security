/**
* UsuarioView.java
* demo
* 5/4/2015 19:46:19
* Copyright David Romero Alcaide
* com.app.ui.user.usuario
*/
package com.app.ui.user.usuario.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.applicationservices.services.UsuarioService;
import com.app.domain.model.types.Usuario;
import com.app.infrastructure.security.Authority;
import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.app.ui.ViewChangeListenerUI;
import com.app.ui.user.UserView;
import com.app.ui.user.menu.MenuComponent;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SpringView(name=Authority.USUARIO)
/**
 * @author Administrador
 *
 */
public class UsuarioView extends HorizontalLayout implements View,UserView{
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MenuComponent menu;
	
	private Usuario currentUser;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6217826766664471645L;
	protected Authority rol;

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
	@PostConstruct
    void init() {
		currentUser = usuarioService.findByUserAccount(AppUI.getCurrentUser());
		generateRol();
		removeAllComponents();
		generateView();
	}

	/**
	 * @author Administrador
	 */
	private void generateView() {
		setSizeFull();
        addStyleName("mainview");
        menu.setCurrentUser(currentUser);
        addComponent(menu);

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);
        NavigatorUI navigator = new NavigatorUI(AppUI.getCurrent(), content);
        navigator.addViewChangeListener(new ViewChangeListenerUI());
        navigator.addProvider(( (AppUI) AppUI.getCurrent()).getSpringViewProvider());
        navigator.addView("PaneldeControl", PanelControlView.class);
        navigator.navigateTo("PaneldeControl");
	}
	
	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.USUARIO);
	}
	
	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#getRol()
	 */
	public Authority getRol() {
		if (this.rol==null){
			generateRol();
		}
		return rol;
	}

}
