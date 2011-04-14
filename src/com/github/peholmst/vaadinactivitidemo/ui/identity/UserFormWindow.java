package com.github.peholmst.vaadinactivitidemo.ui.identity;

import org.activiti.engine.identity.User;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class UserFormWindow extends Window {
	private static final long serialVersionUID = -2197473990599017981L;

	private final User user;

	private final UserCallback saveCallBack;

	private Form userForm;

	private UserFormWindow(String title, User user, UserCallback saveCallback) {
		super(title);
		setModal(true);
		this.user = user;
		this.saveCallBack = saveCallback;
		initComponents();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		userForm = new Form();
		userForm.setFormFieldFactory(createFieldFactory());
		userForm.setInvalidCommitted(false);
		userForm.setWriteThrough(false);
		userForm.setImmediate(true);
		BeanItem<User> item = new BeanItem<User>(user);
		userForm.setItemDataSource(item);
		userForm.setVisibleItemProperties(new String[] { "id", "password",
				"firstName", "lastName", "email" });
		addComponent(userForm);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		addComponent(buttons);

		Button okButton = new Button("Save and Close",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						saveAndClose();
					}
				});
		buttons.addComponent(okButton);

		Button cancelButton = new Button("Close", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				closeWithoutSaving();
			}
		});
		buttons.addComponent(cancelButton);

		((TextField) userForm.getField("id")).selectAll();

		setWidth("300px");
	}

	private void saveAndClose() {
		try {
			userForm.commit();
			saveCallBack.saveUser(user);
			getParent().removeWindow(this);
		} catch (InvalidValueException e) {
			// Ignore this exception; it is handled by the form.
		} catch (RuntimeException e) {
			showNotification(
					String.format("Error saving user: %s", e.getMessage()),
					Notification.TYPE_ERROR_MESSAGE);
		}
	}

	private void closeWithoutSaving() {
		getParent().removeWindow(this);
	}

	@SuppressWarnings("serial")
	private FormFieldFactory createFieldFactory() {
		return new DefaultFieldFactory() {

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				Field f = super.createField(item, propertyId, uiContext);
				if ("id".equals(propertyId) || "password".equals(propertyId)) {
					f.setRequired(true);
					f.setRequiredError(String.format("%s cannot be empty.",
							f.getCaption()));
				}
				if (f instanceof TextField) {
					((TextField) f).setNullRepresentation("");
				}
				return f;
			}
		};
	}

	public static interface UserCallback {
		void saveUser(User user);
	}

	public static UserFormWindow addUserWindow(UserCallback saveCallback,
			User userToEdit) {
		UserFormWindow window = new UserFormWindow("Add User", userToEdit,
				saveCallback);
		return window;
	}

	public static UserFormWindow editUserWindow(UserCallback saveCallback,
			User userToEdit) {
		UserFormWindow window = new UserFormWindow("Edit User", userToEdit,
				saveCallback);
		return window;
	}

}
