package bugrap.ui.identity;

import bugrap.ui.util.AbstractBugrapView;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.Reindeer;

public class IdentityManagementViewImpl extends
		AbstractBugrapView<IdentityManagementView, IdentityManagementPresenter>
		implements IdentityManagementView {

	private static final long serialVersionUID = -7185704857935766773L;

	private TabSheet tabs;

	public IdentityManagementViewImpl() {
		super(true);
	}

	@Override
	protected void initView() {
		super.initView();

		tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addStyleName(Reindeer.TABSHEET_MINIMAL);
		tabs.addTab(createUsersTab(), "Users", null);
		tabs.addTab(createGroupsTab(), "Groups", null);

		getViewLayout().addComponent(tabs);
		getViewLayout().setExpandRatio(tabs, 1.0F);
	}

	private Component createUsersTab() {
		return new UserBrowser();
	}

	private Component createGroupsTab() {
		return new GroupBrowser();
	}

	@Override
	public String getDisplayName() {
		return "Identity Management";
	}

	@Override
	public String getDescription() {
		return "Manage users and groups";
	}

	@Override
	protected IdentityManagementPresenter createPresenter() {
		return new IdentityManagementPresenter(this);
	}

}
