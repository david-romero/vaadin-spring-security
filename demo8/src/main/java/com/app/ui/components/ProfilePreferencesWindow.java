/**
 * ProfilePreferencesWindow.java
 * appEducacionalVaadin
 * 26/12/2014 23:06:10
 * Copyright David
 * com.app.ui.user
 */
package com.app.ui.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.app.domain.model.types.Persona;
import com.app.presenter.event.EventComunication.CloseOpenWindowsEvent;
import com.app.presenter.event.EventComunicationBus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class ProfilePreferencesWindow extends Window {

	private Persona personaAEditar;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1291803436206807702L;

	public static final String ID = "profilepreferenceswindow";

	private final BeanFieldGroup<Persona> fieldGroup;
	/*
	 * Fields for editing the User object are defined here as class members.
	 * They are later bound to a FieldGroup by calling
	 * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
	 * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
	 * the fields with the user object.
	 */
	@PropertyId("nombre")
	private TextField firstNameField;
	@PropertyId("apellidos")
	private TextField lastNameField;
	@PropertyId("email")
	private TextField emailField;
	@PropertyId("telefono")
	private TextField phoneField;
	
	private ProgressBar progressBar;
	
	ByteArrayOutputStream os;
	
	private String filename;
	
	private Image profilePic;

	private ProfilePreferencesWindow(final Persona persona,
			final boolean preferencesTabOpen) {
		os = new ByteArrayOutputStream(1024);
		progressBar = new ProgressBar();
		personaAEditar = persona;

		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(false);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		detailsWrapper.addComponent(buildProfileTab());
		detailsWrapper.addComponent(buildPreferencesTab());

		if (preferencesTabOpen) {
			detailsWrapper.setSelectedTab(1);
		}

		content.addComponent(buildFooter());

		fieldGroup = new BeanFieldGroup<Persona>(Persona.class);
		fieldGroup.bindMemberFields(this);
		fieldGroup.setItemDataSource(personaAEditar);

	}

	private Component buildPreferencesTab() {
		VerticalLayout root = new VerticalLayout();
		root.setCaption("Preferences");
		root.setIcon(FontAwesome.COGS);
		root.setSpacing(true);
		root.setMargin(true);
		root.setSizeFull();

		Label message = new Label("Not implemented in this demo");
		message.setSizeUndefined();
		message.addStyleName(ValoTheme.LABEL_LIGHT);
		root.addComponent(message);
		root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

		return root;
	}

	private Component buildProfileTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Profile");
		root.setIcon(FontAwesome.USER);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addStyleName("profile-form");

		VerticalLayout pic = new VerticalLayout();
		pic.setSizeUndefined();
		pic.setSpacing(true);
		Resource source;
		if (personaAEditar.getImagen() != null
				&& personaAEditar.getImagen().length > 0) {
			StreamSource source2 = new StreamResource.StreamSource() {

				private static final long serialVersionUID = -3823582436185258502L;

				public InputStream getStream() {
					InputStream reportStream = null;
					reportStream = new ByteArrayInputStream(
							personaAEditar.getImagen());
					return reportStream;
				}
			};
			source = new StreamResource(source2, "profile-picture.png");
		} else {
			source = new ThemeResource("img/profile-pic-300px.jpg");
		}
		profilePic = new Image("", source);
		profilePic.setWidth(100.0f, Unit.PIXELS);
		profilePic.markAsDirty();
		pic.addComponent(profilePic);

		Upload upload = new Upload("", new Receiver() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -9072669962584531985L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				os.reset();
				progressBar = new ProgressBar(0.0f);
				progressBar.setVisible(true);
				return os;
			}
		});
		upload.addSucceededListener(new SucceededListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -129726637043975522L;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				Resource source = new StreamResource(
		                new StreamResource.StreamSource() {
		                    /**
							 * 
							 */
							private static final long serialVersionUID = 3561230243878849854L;

							public InputStream getStream() {
		                        return new ByteArrayInputStream(os.toByteArray());
		                    }
		                }, filename) {
		            /**
							 * 
							 */
							private static final long serialVersionUID = -2835737445785315855L;

					@Override
		            public String getMIMEType() {
		                return "image/png";
		            }
		        };
		        
		        profilePic.setSource(source);
		        profilePic.markAsDirty();
		        profilePic.setSizeUndefined();
		        profilePic.setWidth(100.0f, Unit.PIXELS);
			}
		});
		upload.addProgressListener(new ProgressListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -2381209050087325148L;

			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (contentLength == -1)
                    progressBar.setIndeterminate(true);
                else {
                	progressBar.setIndeterminate(false);
                	progressBar.setValue(((float)readBytes) /
                                      ((float)contentLength));
                }
			}
		});
		upload.setImmediate(true);
		upload.setButtonCaption("Cambiar...");
		pic.addComponent(upload);
		progressBar.setVisible(false);
		pic.addComponent(progressBar);

		root.addComponent(pic);

		// root.addComponent(upload);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		firstNameField = new TextField("Nombre");
		details.addComponent(firstNameField);
		lastNameField = new TextField("Apellidos");
		details.addComponent(lastNameField);

		Label section = new Label("Información personal");
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_COLORED);
		details.addComponent(section);

		emailField = new TextField("Email");
		emailField.setWidth("100%");
		emailField.setRequired(true);
		emailField.setNullRepresentation("");
		details.addComponent(emailField);

		phoneField = new TextField("Teléfono");
		phoneField.setWidth("100%");
		phoneField.setNullRepresentation("");
		details.addComponent(phoneField);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					// Updated user should also be persisted to database. But
					// not in this demo.

					Notification success = new Notification(
							"Profile updated successfully");
					success.setDelayMsec(2000);
					success.setStyleName("bar success small");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());
					
					close();
				} catch (CommitException e) {
					e.printStackTrace();
					Notification.show("Error while updating profile",
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		Button cancel = new Button("Cancelar");
		cancel.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cancel.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();
				// Updated user should also be persisted to database. But
				// not in this demo.

				// AppEducacionalEventBus.post(new ProfileUpdatedEvent());
				close();

			}
		});
		footer.addComponent(cancel);
		HorizontalLayout footersButtons = new HorizontalLayout();
		footersButtons.addComponent(ok);
		footersButtons.addComponent(cancel);
		footersButtons.setSpacing(true);
		footer.addComponent(footersButtons);
		footer.setComponentAlignment(footersButtons, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final Persona user,
			final boolean preferencesTabActive) {
		EventComunicationBus.post(new CloseOpenWindowsEvent());
		Window w = new ProfilePreferencesWindow(user, preferencesTabActive);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
