package bugrap.identity;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class IdentityManagementView extends VerticalLayout {

	private static final long serialVersionUID = -7185704857935766773L;

	private TabSheet tabs;

	public IdentityManagementView() {
		init();
	}

	private void init() {
		tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addStyleName(Reindeer.TABSHEET_MINIMAL);
		tabs.addTab(createUsersTab(), "Users", null);
		tabs.addTab(createGroupsTab(), "Groups", null);

		addComponent(tabs);
		setSizeFull();
		setMargin(true);
	}

	private Component createUsersTab() {
		return new UserBrowser();
	}

	private Component createGroupsTab() {
		return new GroupBrowser();
	}

}
