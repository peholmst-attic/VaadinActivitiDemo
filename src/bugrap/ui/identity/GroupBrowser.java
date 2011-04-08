package bugrap.ui.identity;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class GroupBrowser extends VerticalLayout {

	private static final long serialVersionUID = 3055592040177690110L;

	private Table groupTable;

	private Button addGroupButton;

	private Button editGroupButton;

	private Button removeGroupButton;

	public GroupBrowser() {
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

		addGroupButton = createAddGroupButton();
		toolbar.addComponent(addGroupButton);

		editGroupButton = createEditGroupButton();
		toolbar.addComponent(editGroupButton);

		removeGroupButton = createRemoveGroupButton();
		toolbar.addComponent(removeGroupButton);

		addComponent(toolbar);
	}

	@SuppressWarnings("serial")
	private void initTable() {
		groupTable = new Table();
		groupTable.setSizeFull();
		groupTable.setSelectable(true);
		groupTable.setImmediate(true);
		groupTable.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateButtonStates();
			}
		});
		addComponent(groupTable);
		setExpandRatio(groupTable, 1.0F);
		populateTable();
	}

	private void populateTable() {
		GroupQuery query = getIdentityService().createGroupQuery();
		List<Group> allGroups = query.orderByGroupId().asc().list();
		BeanItemContainer<Group> dataSource = new BeanItemContainer<Group>(
				Group.class, allGroups);
		groupTable.setContainerDataSource(dataSource);
		groupTable.setVisibleColumns(new String[] { "id", "name", "type" });
		updateButtonStates();
	}

	@SuppressWarnings("serial")
	private Button createAddGroupButton() {
		Button button = new Button("Add Group");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				addGroup();
			}
		});
		return button;
	}

	private void addGroup() {
		Group newGroup = getIdentityService().newGroup("newGroup");
		GroupFormWindow addGroupWindow = GroupFormWindow.addGroupWindow(
				new GroupFormWindow.GroupCallback() {

					@Override
					public void saveGroup(Group group) {
						getIdentityService().saveGroup(group);
						populateTable();
					}
				}, newGroup);
		getWindow().addWindow(addGroupWindow);
	}

	@SuppressWarnings("serial")
	private Button createEditGroupButton() {
		Button button = new Button("Edit Group");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editGroup();
			}
		});
		return button;
	}

	private void editGroup() {
		Group selectedGroup = (Group) groupTable.getValue();
		if (selectedGroup != null) {
			GroupFormWindow editGroupWindow = GroupFormWindow.editGroupWindow(
					new GroupFormWindow.GroupCallback() {

						@Override
						public void saveGroup(Group group) {
							getIdentityService().saveGroup(group);
							populateTable();
						}
					}, selectedGroup);
			getWindow().addWindow(editGroupWindow);
		}
	}

	@SuppressWarnings("serial")
	private Button createRemoveGroupButton() {
		Button button = new Button("Remove Group");
		button.addStyleName(Reindeer.BUTTON_SMALL);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				removeGroup();
			}
		});
		return button;
	}

	private void removeGroup() {
		Group selectedGroup = (Group) groupTable.getValue();
		if (selectedGroup != null) {
			getIdentityService().deleteGroup(selectedGroup.getId());
			populateTable();
		}
	}

	private void updateButtonStates() {
		boolean isGroupSelected = groupTable.getValue() != null;
		editGroupButton.setEnabled(isGroupSelected);
		removeGroupButton.setEnabled(isGroupSelected);
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}

}
