/**
 * PanelControlPresenter.java
 * appEducacionalVaadin
 * 7/2/2015 18:13:51
 * Copyright David
 * com.app.ui.user.panelControl.presenter
 */
package com.app.ui.user.panelControl.presenter;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.applicationservices.services.AdministradorService;
import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.Persona;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
/**
 * @author David
 *
 */
public class PanelControlPresenter implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3060765199303204446L;
	@Autowired
	private AdministradorService adminService;


	public PanelControlPresenter() {
	}

	/**
	 * @author David
	 * @return
	 */
	public int getNotificacionesNoLeidas() {
		return 3;
	}

	/**
	 * @author David
	 * @return
	 */
	public List<?> getListNotificacionesNoLeidas() {
		List<?> list = Lists.newArrayList();
		return list;
	}



	/**
	 * @author David
	 */
	public void savePerson(Persona p) {
		adminService.save( (Administrador) p);
	}

}
