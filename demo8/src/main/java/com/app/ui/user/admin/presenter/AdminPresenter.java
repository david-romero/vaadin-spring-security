/**
 * AdminPresenter.java
 * appEducacionalVaadin
 * 21/3/2015 20:09:18
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.admin.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.applicationservices.services.AdministradorService;
import com.app.applicationservices.services.PersonaService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Persona;
import com.app.infrastructure.security.UserAccount;
import com.app.ui.AppUI;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;

@SpringComponent
@ViewScope
/**
 * @author David
 *
 */
public class AdminPresenter implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3168752163649288162L;
	@Autowired
	private AdministradorService adminService;
	
	@Autowired
	private PersonaService personaService;


	public Administrador getAdministrador() {
		UserAccount account = AppUI.getCurrentUser();
		com.app.domain.model.types.Administrador p = adminService
				.findByUserAccount(account);
		return p;
	}

	

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		//Id de la persona
		String idString = event.getButton().getId();
		@SuppressWarnings("unused")
		Integer id = new Integer(idString);
		
		Table table = (Table) event.getButton().getParent();
		table.refreshRowCache();
	}



	/**
	 * @author Administrador
	 * @return
	 */
	public Collection<Persona> getTodasPersonas() {
		return personaService.findAll();
	}

}
