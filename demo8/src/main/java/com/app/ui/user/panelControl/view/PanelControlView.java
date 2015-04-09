/**
 * PanelControlView.java
 * appEducacionalVaadin
 * 11/1/2015 19:59:07
 * Copyright David
 * com.app.ui.user.panelControl
 */
package com.app.ui.user.panelControl.view;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.presenter.event.EventComunication.CloseOpenWindowsEvent;
import com.app.presenter.event.EventComunication.NotificationsCountUpdatedEvent;
import com.app.presenter.event.EventComunicationBus;
import com.app.ui.components.TopTenMoviesTable;
import com.app.ui.user.panelControl.presenter.PanelControlPresenter;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name="PaneldeControl")
/**
 * @author David
 *
 */
public class PanelControlView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3179977221799819013L;
	public static final String EDIT_ID = "dashboard-edit";
	public static final String TITLE_ID = "dashboard-title";

	@Autowired
	private PanelControlPresenter presenter;

	private Label titleLabel;
	private NotificationsButton notificationsButton;
	private NotificationCreateButton notificationCreateButton;
	private CssLayout dashboardPanels;
	private VerticalLayout root;
	private Window notificationsWindow;
	
	@Override
	public void enter(final ViewChangeEvent event) {
	}
	
	@PostConstruct
    void init() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		EventComunicationBus.register(this);

		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("appeducacional-view");
		setContent(root);
		Responsive.makeResponsive(root);

		root.addComponent(buildHeader());

		root.addComponent(buildSparklines());

		Component content = buildContent();
		root.addComponent(content);
		root.setExpandRatio(content, 1);

		// All the open sub-windows should be closed whenever the root layout
		// gets clicked.
		root.addLayoutClickListener(e->{
			EventComunicationBus.post(new CloseOpenWindowsEvent());
		});
		notificationsButton.updateNotificationsCount(null);
	}

	private Component buildSparklines() {
		CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");
        Responsive.makeResponsive(sparks);

        return sparks;
	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);

		titleLabel = new Label("Panel de Control");
		titleLabel.setId(TITLE_ID);
		titleLabel.setSizeUndefined();
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
		titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(titleLabel);

		notificationsButton = buildNotificationsButton();
		notificationCreateButton = buildNotificationCreateButton();
		HorizontalLayout tools = new HorizontalLayout(notificationsButton,notificationCreateButton);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private NotificationsButton buildNotificationsButton() {
		NotificationsButton result = new NotificationsButton();
		result.addClickListener(e->{
			openNotificationsPopup(e);
		});
		return result;
	}
	
	private NotificationCreateButton buildNotificationCreateButton() {
		NotificationCreateButton result = new NotificationCreateButton();
		result.addClickListener(e->{
			//TODO
		});
		return result;
	}


	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("appeducacional-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(buildTopGrossingMovies());
		dashboardPanels.addComponent(buildNotes());
		dashboardPanels.addComponent(buildTop10TitlesByRevenue());
		dashboardPanels.addComponent(buildPopularMovies());

		return dashboardPanels;
	}

	private Component buildTopGrossingMovies() {
		Label topGrossingMoviesChart = new Label();
		topGrossingMoviesChart.setSizeFull();
		return createContentWrapper(topGrossingMoviesChart);
	}

	private Component buildNotes() {
		TextArea notes = new TextArea("Notes");
		String value = "Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater";
		notes.setValue(value);
		notes.setSizeFull();
		notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		Component panel = createContentWrapper(notes);
		panel.addStyleName("notes");
		return panel;
	}

	private Component buildTop10TitlesByRevenue() {
		Component contentWrapper = createContentWrapper(new TopTenMoviesTable());
		contentWrapper.addStyleName("top10-revenue");
		return contentWrapper;
	}

	private Component buildPopularMovies() {
		return createContentWrapper(new Label());
	}

	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("appeducacional-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("appeducacional-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		if ( content instanceof TextArea ){
			MenuItem save = tools.addItem("", FontAwesome.SAVE,new Command() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
					@SuppressWarnings("unused")
					String notes = ((TextArea)content).getValue();
				}
			});
			save.setStyleName("icon-only");
		}
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1943034390434178282L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!slot.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot, true);
				} else {
					slot.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot, false);
				}
			}
		});
		max.setStyleName("icon-only");
		MenuItem root = tools.addItem("", FontAwesome.COG, null);
		root.addItem("Configure", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6850093122298654078L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});
		root.addSeparator();
		root.addItem("Close", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2993982684259298243L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}
	
	

	private void openNotificationsPopup(final ClickEvent event) {
		VerticalLayout notificationsLayout = new VerticalLayout();
		notificationsLayout.setMargin(true);
		notificationsLayout.setSpacing(true);

		Label title = new Label("Notificaciones no leídas");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		notificationsLayout.addComponent(title);
		

		EventComunicationBus.post(new NotificationsCountUpdatedEvent());

		

		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth("100%");
		Button showAll = new Button("Ver Notificaciones");
		showAll.addClickListener(e->{
			notificationsWindow.close();	
		});
		showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		showAll.addStyleName(ValoTheme.BUTTON_SMALL);
		footer.addComponent(showAll);
		footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
		notificationsLayout.addComponent(footer);

		if (notificationsWindow == null) {
			notificationsWindow = new Window();
			notificationsWindow.setWidth(300.0f, Unit.PIXELS);
			notificationsWindow.addStyleName("notifications");
			notificationsWindow.setClosable(false);
			notificationsWindow.setResizable(false);
			notificationsWindow.setDraggable(false);
			notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
			notificationsWindow.setContent(notificationsLayout);
		}

		if (!notificationsWindow.isAttached()) {
			notificationsWindow.setPositionY(event.getClientY()
					- event.getRelativeY() + 40);
			getUI().addWindow(notificationsWindow);
			notificationsWindow.focus();
		} else {
			notificationsWindow.close();
		}
	}

	

	/*
	 * @Override public void dashboardNameEdited(final String name) {
	 * titleLabel.setValue(name); }
	 */

	private void toggleMaximized(final Component panel, final boolean maximized) {
		for (Iterator<Component> it = root.iterator(); it.hasNext();) {
			it.next().setVisible(!maximized);
		}
		dashboardPanels.setVisible(true);
		dashboardPanels.forEach(componente ->{
			componente.setVisible(!maximized);
		});

		if (maximized) {
			panel.setVisible(true);
			panel.addStyleName("max");
		} else {
			panel.removeStyleName("max");
		}
	}

	public final class NotificationsButton extends Button {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2993136376885547514L;
		private static final String STYLE_UNREAD = "unread";
		public static final String ID = "dashboard-notifications";

		public NotificationsButton() {
			setIcon(FontAwesome.BELL);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			EventComunicationBus.register(this);
		}

		@Subscribe
		public void updateNotificationsCount(
				final NotificationsCountUpdatedEvent event) {
			setUnreadCount(presenter.getNotificacionesNoLeidas());
		}

		public void setUnreadCount(final int count) {
			setCaption(String.valueOf(count));

			String description = "Notifications";
			if (count > 0) {
				addStyleName(STYLE_UNREAD);
				description += " (" + count + " unread)";
			} else {
				removeStyleName(STYLE_UNREAD);
			}
			setDescription(description);
		}
	}
	
	public static final class NotificationCreateButton extends Button {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2993136376885547514L;
		public static final String ID = "dashboard-notifications";

		public NotificationCreateButton() {
			setIcon(FontAwesome.SEND);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			EventComunicationBus.register(this);
		}

	}

}
