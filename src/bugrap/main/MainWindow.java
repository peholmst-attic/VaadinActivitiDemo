package bugrap.main;

import bugrap.identity.IdentityManagementView;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class MainWindow extends Window {

	private static final long serialVersionUID = 7868596379434577222L;

	public MainWindow() {
		super("Bugrap Activiti Demo");
		init();
	}

	private void init() {
		setSizeFull();
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		layout.setSizeFull();

		WindowHeader header = createWindowHeader();
		layout.addComponent(header);

		TabSheet viewTabs = createViewTabs();
		layout.addComponent(viewTabs);
		layout.setExpandRatio(viewTabs, 1.0f);
	}

	private WindowHeader createWindowHeader() {
		return new WindowHeader();
	}

	private TabSheet createViewTabs() {
		TabSheet tabs = new TabSheet();
		tabs.addStyleName(Reindeer.TABSHEET_SMALL);
		tabs.setSizeFull();

		tabs.addTab(createIdentityManagementView(), "Identity Management", null);

		return tabs;
	}

	private IdentityManagementView createIdentityManagementView() {
		return new IdentityManagementView();
	}
}
