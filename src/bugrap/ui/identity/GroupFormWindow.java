package bugrap.ui.identity;

import org.activiti.engine.identity.Group;

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

public class GroupFormWindow extends Window {

	private static final long serialVersionUID = -2197473990599017981L;

	private final Group group;

	private final GroupCallback saveCallBack;

	private Form groupForm;

	private GroupFormWindow(String title, Group group,
			GroupCallback saveCallback) {
		super(title);
		setModal(true);
		this.group = group;
		this.saveCallBack = saveCallback;
		initComponents();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		groupForm = new Form();
		groupForm.setFormFieldFactory(createFieldFactory());
		groupForm.setInvalidCommitted(false);
		groupForm.setWriteThrough(false);
		groupForm.setImmediate(true);
		BeanItem<Group> item = new BeanItem<Group>(group);
		groupForm.setItemDataSource(item);
		groupForm
				.setVisibleItemProperties(new String[] { "id", "name", "type" });
		addComponent(groupForm);

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

		((TextField) groupForm.getField("id")).selectAll();

		setWidth("300px");
	}

	private void saveAndClose() {
		try {
			groupForm.commit();
			saveCallBack.saveGroup(group);
			getParent().removeWindow(this);
		} catch (InvalidValueException e) {
			// Ignore this exception; it is handled by the form.
		} catch (RuntimeException e) {
			showNotification(
					String.format("Error saving group: %s", e.getMessage()),
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
				if ("id".equals(propertyId) || "name".equals(propertyId)) {
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

	public static interface GroupCallback {
		void saveGroup(Group group);
	}

	public static GroupFormWindow addGroupWindow(GroupCallback saveCallback,
			Group groupToEdit) {
		GroupFormWindow window = new GroupFormWindow("Add Group", groupToEdit,
				saveCallback);
		return window;
	}

	public static GroupFormWindow editGroupWindow(GroupCallback saveCallback,
			Group groupToEdit) {
		GroupFormWindow window = new GroupFormWindow("Edit Group", groupToEdit,
				saveCallback);
		return window;
	}
}
