/**
* LoginFirm.java
* appEducacionalVaadin
* 10/1/2015 0:51:13
* Copyright David
* com.app.ui.login
*/
package com.app.ui.login.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
/**
 * 
 * @author DRA
 *
 */
public class LoginForm extends VerticalLayout {
	
	private LoginView view;
	
	public LoginForm(LoginView viewBelong){
		this.view = viewBelong;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2405284242525298112L;
	private TextField txtLogin;
	private PasswordField txtPassword;
	private CheckBox rememberMe;

	public TextField getTxtLogin() {
		return txtLogin;
	}

	public PasswordField getTxtPassword() {
		return txtPassword;
	}

	private Component buildFields() {
		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		txtLogin = new TextField("Usuario");
		txtLogin.setIcon(FontAwesome.USER);
		txtLogin.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		txtPassword = new PasswordField("Contraseña");
		txtPassword.setIcon(FontAwesome.LOCK);
		txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final Button signin = new Button("Acceder");
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(txtLogin, txtPassword, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		signin.addClickListener(view);
		return fields;
	}

	private Component buildLabels() {
		CssLayout labels = new CssLayout();
		labels.addStyleName("labels");

		Label welcome = new Label("Bienvenido");
		welcome.setSizeUndefined();
		welcome.addStyleName(ValoTheme.LABEL_H4);
		welcome.addStyleName(ValoTheme.LABEL_COLORED);
		labels.addComponent(welcome);

		Label title = new Label("Dashboard");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_LIGHT);
		labels.addComponent(title);
		return labels;
	}

	@Override
	public void attach() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName("login-panel");

		loginPanel.addComponent(buildLabels());
		loginPanel.addComponent(buildFields());
		
		loginPanel.addComponent(buildFieldRememberMe());
		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	private CheckBox buildFieldRememberMe() {
		rememberMe = new CheckBox("Recordar", true);
		return rememberMe;
	}

	/**
	 * @return rememberMe
	 */
	public CheckBox getRememberMe() {
		return rememberMe;
	}
	
	
}
