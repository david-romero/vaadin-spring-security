/**
 * AdminView.java
 * appEducacionalVaadin
 * 06/12/2014 14:05:37
 * Copyright David
 * com.app.ui.user.admin
 */
package com.app.ui.user.admin.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.domain.model.types.Administrador;
import com.app.infrastructure.security.Authority;
import com.app.ui.AppUI;
import com.app.ui.NavigatorUI;
import com.app.ui.ViewChangeListenerUI;
import com.app.ui.user.admin.presenter.AdminPresenter;
import com.app.ui.user.admin.view.banear.usuarios.BanearUsuarioView;
import com.app.ui.user.menu.MenuComponent;
import com.app.ui.user.panelControl.view.PanelControlView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SpringView(name=Authority.ADMINISTRADOR)
/**
 * @author David
 *
 */
public class AdminView extends HorizontalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830300048826230639L;
	
	public static final String NAME = "admin";
	
	protected Authority rol;
	@Autowired
	private AdminPresenter presenter;
	@Autowired
	private MenuComponent menu;

	private Administrador currentUser;
	
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		
	}
	
	@PostConstruct
    void init() {
		currentUser = presenter.getAdministrador();
		generateRol();
		removeAllComponents();
		generateView();
	}
	

	/**
	 * @author David
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
        navigator.addView("BanearUsuario", BanearUsuarioView.class);
        navigator.addView("PaneldeControl", PanelControlView.class);
        navigator.navigateTo("PaneldeControl");
        
	}

	/* (non-Javadoc)
	 * @see com.app.ui.user.UserAbstractView#generateRol()
	 */
	public void generateRol() {
		this.rol = new Authority();
		this.rol.setAuthority(Authority.ADMINISTRADOR);
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
