/**
 * MenuComponent.java
 * appEducacionalVaadin
 * 19/12/2014 23:55:58
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.user.menu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.domain.model.types.Persona;
import com.app.infrastructure.security.AuthManager;
import com.app.infrastructure.security.Authority;
import com.app.presenter.event.EventComunication.NotificationsCountUpdatedEvent;
import com.app.presenter.event.EventComunication.PostViewChangeEvent;
import com.app.presenter.event.EventComunication.ProfileUpdatedEvent;
import com.app.presenter.event.EventComunication.ReportsCountUpdatedEvent;
import com.app.presenter.event.EventComunication.UserLoggedOutEvent;
import com.app.presenter.event.EventComunicationBus;
import com.app.ui.components.ProfilePreferencesWindow;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@ViewScope
/**
 * @author David
 *
 */
public class MenuComponent extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078384311201893042L;

	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private static final String STYLE_VISIBLE = "valo-menu-visible";
	private Label notificationsBadge;
	private Label reportsBadge;
	private MenuItem settingsItem;
	
	private Persona currentUser;
	
	@Autowired
	private AuthManager authManager;

	public MenuComponent() {
	}
	
	/**
	 * @param currentUser the currentUser to set
	 * Establecer el currentUser
	 */
	public void setCurrentUser(Persona currentUser) {
		this.currentUser = currentUser;
	}
	
	


	private Component buildContent() {
		final CssLayout menuContent = new CssLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.addStyleName("no-vertical-drag-hints");
		menuContent.addStyleName("no-horizontal-drag-hints");
		menuContent.setWidth(null);
		menuContent.setHeight("100%");

		menuContent.addComponent(buildTitle());
		menuContent.addComponent(buildUserMenu());
		menuContent.addComponent(buildToggleButton());
		menuContent.addComponent(buildMenuItems());

		return menuContent;
	}

	private Component buildTitle() {
		Label logo = new Label("<strong>Dashboard</strong>",
				ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		return logoWrapper;
	}

	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		if (currentUser.getImagen() != null && currentUser.getImagen().length > 0) {
			com.vaadin.server.StreamResource source;
			StreamSource source2 = new StreamResource.StreamSource() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -3823582436185258502L;

				public InputStream getStream() {
					InputStream reportStream = null;
					reportStream = new ByteArrayInputStream(currentUser.getImagen());
					return reportStream;
				}
			};
			source = new StreamResource(source2, "profile-picture.png");
			settingsItem = settings.addItem("", source, null);
		} else {
			settingsItem = settings.addItem("", new ThemeResource(
					"img/profile-pic-300px.jpg"), null);
		}
		updateUserName(null);
		settingsItem.addItem("Editar Perfil", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5784771381981479180L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				ProfilePreferencesWindow.open(currentUser, false);
			}
		});
		settingsItem.addItem("Preferiencias", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5102562606395914017L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				ProfilePreferencesWindow.open(currentUser, true);
			}
		});
		settingsItem.addSeparator();
		settingsItem.addItem("Salir", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1740304688423596121L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				authManager.removeUserTokens(currentUser.getUserAccount().getUsername());
				EventComunicationBus.post(new UserLoggedOutEvent());
			}
		});
		return settings;
	}

	private Component buildToggleButton() {
		Button valoMenuToggleButton = new Button("Menu");
		valoMenuToggleButton.addClickListener(e->{
			if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
				getCompositionRoot().removeStyleName(STYLE_VISIBLE);
			} else {
				getCompositionRoot().addStyleName(STYLE_VISIBLE);
			}
		 });
		valoMenuToggleButton.setIcon(FontAwesome.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		return valoMenuToggleButton;
	}

	private Component buildMenuItems() {
		CssLayout menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");
		menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);
		
		for (final UserMenuHelper view : UserMenuHelper.values()) {
			Authority auth = Lists.newArrayList(
					currentUser.getUserAccount().getAuthorities()).get(0);
			boolean tienePermisos = view.getRoles().contains(auth);
			if (tienePermisos) {
				Component menuItemComponent = new ValoMenuItemButton(view);

				

				if (view == UserMenuHelper.PANELCONTROL) {
					notificationsBadge = new Label();
					notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
					menuItemComponent = buildBadgeWrapper(menuItemComponent,
							notificationsBadge);
				}

				menuItemsLayout.addComponent(menuItemComponent);
			}
		}
		return menuItemsLayout;

	}

	private Component buildBadgeWrapper(final Component menuItemButton,
			final Component badgeLabel) {
		CssLayout dashboardWrapper = new CssLayout(menuItemButton);
		dashboardWrapper.addStyleName("badgewrapper");
		dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
		dashboardWrapper.setWidth(100.0f, Unit.PERCENTAGE);
		badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
		badgeLabel.setWidthUndefined();
		badgeLabel.setVisible(false);
		dashboardWrapper.addComponent(badgeLabel);
		return dashboardWrapper;
	}

	@Override
	public void attach() {
		super.attach();
		addStyleName("valo-menu");
		setId(ID);
		setSizeUndefined();

		// There's only one DashboardMenu per UI so this doesn't need to be
		// unregistered from the UI-scoped DashboardEventBus.
		// DashboardEventBus.register(this);

		setCompositionRoot(buildContent());
		updateNotificationsCount(null);
	}

	@Subscribe
	public void postViewChange(final PostViewChangeEvent event) {
		// After a successful view change the menu can be hidden in mobile view.
		getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	}

	@Subscribe
	public void updateNotificationsCount(
			final NotificationsCountUpdatedEvent event) {

		int unreadNotificationsCount = 5;
		notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
		notificationsBadge.setVisible(unreadNotificationsCount > 0);
	}

	@Subscribe
	public void updateReportsCount(final ReportsCountUpdatedEvent event) {
		reportsBadge.setValue(String.valueOf(event.getCount()));
		reportsBadge.setVisible(event.getCount() > 0);
	}

	@Subscribe
	public void updateUserName(final ProfileUpdatedEvent event) {
		settingsItem.setText(currentUser.getUserAccount().getUsername());
	}

	public final class ValoMenuItemButton extends Button {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5684186527583637546L;

		private static final String STYLE_SELECTED = "selected";

		private final UserMenuHelper view;

		public ValoMenuItemButton(final UserMenuHelper view) {
			this.view = view;
			setPrimaryStyleName("valo-menu-item");
			setIcon(view.getIcon());
			setCaption(view.getViewName().substring(0, 1).toUpperCase()
					+ view.getViewName().substring(1));
			EventComunicationBus.register(this);
			addClickListener(e->{
				UI.getCurrent().getNavigator()
				.navigateTo(view.getViewName());
			});
		}

		@Subscribe
		public void postViewChange(final PostViewChangeEvent event) {
			removeStyleName(STYLE_SELECTED);
			if (event.getView() == view) {
				addStyleName(STYLE_SELECTED);
			}
		}
	}
}
