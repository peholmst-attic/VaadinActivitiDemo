package bugrap.ui.identity;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class UserBrowser extends VerticalLayout {

	private static final long serialVersionUID = 5324611555684319728L;

	private Table userTable;

	private Button addUserButton;

	private Button editUserButton;

	private Button removeUserButton;

	private Button manageGroupMembershipsButton;

	public UserBrowser() {
		init();
	}

	private void init() {
		setSizeFull();
		initToolbar();
		initTable();
	}

	private void initToolbar() {
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setMargin(true, false, true, false);
		toolbar.setSpacing(true);

		addUserButton = createAddUserButton();
		toolbar.addComponent(addUserButton);

		editUserButton = createEditUserButton();
		toolbar.addComponent(editUserButton);

		removeUserButton = createRemoveUserButton();
		toolbar.addComponent(removeUserButton);

		manageGroupMembershipsButton = createManageGroupMembershipsButton();
		toolbar.addComponent(manageGroupMembershipsButton);

		addComponent(toolbar);
	}

	@SuppressWarnings("serial")
	private void initTable() {
		userTable = new Table();
		userTable.setSizeFull();
		userTable.setSelectable(true);
		userTable.setImmediate(true);
		userTable.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateButtonStates();
			}
		});
		addComponent(userTable);
		setExpandRatio(userTable, 1.0F);
		populateTable();
	}

	private void populateTable() {
		UserQuery query = getIdentityService().createUserQuery();
		List<User> allUsers = query.orderByUserId().asc().list();
		BeanItemContainer<User> dataSource = new BeanItemContainer<User>(
				User.class, allUsers);
		userTable.setContainerDataSource(dataSource);
		userTable.setVisibleColumns(new String[] { "id", "firstName",
				"lastName", "email" });
		updateButtonStates();
	}

	@SuppressWarnings("serial")
	private Button createAddUserButton() {
		Button button = new Button("Add User");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				addUser();
			}
		});
		return button;
	}

	private void addUser() {
		User newUser = getIdentityService().newUser("newUser");
		UserFormWindow addUserWindow = UserFormWindow.addUserWindow(
				new UserFormWindow.UserCallback() {

					@Override
					public void saveUser(User user) {
						getIdentityService().saveUser(user);
						populateTable();
					}
				}, newUser);
		getWindow().addWindow(addUserWindow);
	}

	@SuppressWarnings("serial")
	private Button createEditUserButton() {
		Button button = new Button("Edit User");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editUser();
			}
		});
		return button;
	}

	private void editUser() {
		User selectedUser = (User) userTable.getValue();
		if (selectedUser != null) {
			UserFormWindow editUserWindow = UserFormWindow.editUserWindow(
					new UserFormWindow.UserCallback() {

						@Override
						public void saveUser(User user) {
							getIdentityService().saveUser(user);
							populateTable();
						}
					}, selectedUser);
			getWindow().addWindow(editUserWindow);

		}
	}

	@SuppressWarnings("serial")
	private Button createRemoveUserButton() {
		Button button = new Button("Remove User");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				removeUser();
			}
		});
		return button;
	}

	private void removeUser() {
		User selectedUser = (User) userTable.getValue();
		if (selectedUser != null) {
			getIdentityService().deleteUser(selectedUser.getId());
			populateTable();
		}
	}

	@SuppressWarnings("serial")
	private Button createManageGroupMembershipsButton() {
		Button button = new Button("Manage Group Memberships");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				manageGroupMemberships();
			}
		});
		return button;
	}

	private void manageGroupMemberships() {
		User selectedUser = (User) userTable.getValue();
		if (selectedUser != null) {
			GroupMembershipWindow groupMembershipWindow = new GroupMembershipWindow(
					selectedUser.getId());
			getWindow().addWindow(groupMembershipWindow);
		}
	}

	private void updateButtonStates() {
		boolean isUserSelected = userTable.getValue() != null;
		editUserButton.setEnabled(isUserSelected);
		removeUserButton.setEnabled(isUserSelected);
		manageGroupMembershipsButton.setEnabled(isUserSelected);
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}

}
