package bugrap.ui.identity;

import com.github.peholmst.mvp4vaadin.VaadinView;
import com.github.peholmst.mvp4vaadin.navigation.AbstractControllableView;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class IdentityManagementViewImpl
		extends
		AbstractControllableView<IdentityManagementView, IdentityManagementPresenter>
		implements VaadinView, IdentityManagementView {

	private static final long serialVersionUID = -7185704857935766773L;

	private VerticalLayout viewLayout;

	private TabSheet tabs;

	public IdentityManagementViewImpl() {
		super(true);
	}

	@Override
	protected void initView() {
		viewLayout = new VerticalLayout();
		viewLayout.setSizeFull();
		viewLayout.setMargin(true);

		Label header = new Label(getDisplayName());
		header.addStyleName(Reindeer.LABEL_H1);
		viewLayout.addComponent(header);

		tabs = new TabSheet();
		tabs.setSizeFull();
		tabs.addStyleName(Reindeer.TABSHEET_MINIMAL);
		tabs.addTab(createUsersTab(), "Users", null);
		tabs.addTab(createGroupsTab(), "Groups", null);

		viewLayout.addComponent(tabs);
		viewLayout.setExpandRatio(tabs, 1.0F);
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

	@Override
	public ComponentContainer getViewComponent() {
		return viewLayout;
	}

}
