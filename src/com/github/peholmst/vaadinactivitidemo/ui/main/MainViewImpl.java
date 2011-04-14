package com.github.peholmst.vaadinactivitidemo.ui.main;

import com.github.peholmst.mvp4vaadin.AbstractView;
import com.github.peholmst.mvp4vaadin.VaadinView;
import com.github.peholmst.mvp4vaadin.navigation.ViewProvider;
import com.github.peholmst.mvp4vaadin.navigation.ui.NavigationBar;
import com.github.peholmst.mvp4vaadin.navigation.ui.ViewContainerComponent;
import com.github.peholmst.vaadinactivitidemo.ui.home.HomeViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.main.components.WindowHeader;
import com.vaadin.Application;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

public class MainViewImpl extends AbstractView<MainView, MainPresenter>
		implements MainView, VaadinView {

	private static final long serialVersionUID = -7679616805610225725L;

	private VerticalLayout viewLayout;

	private WindowHeader windowHeader;

	private NavigationBar navigationBar;

	private ViewContainerComponent viewContainer;

	private final Application application;

	private final ViewProvider viewProvider;

	public MainViewImpl(Application application, ViewProvider viewProvider) {
		this.application = application;
		this.viewProvider = viewProvider;
		init();
	}

	@Override
	public String getDisplayName() {
		return "Vaadin Activiti Demo";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	protected MainPresenter createPresenter() {
		return new MainPresenter(this, application, viewProvider);
	}

	@Override
	protected void initView() {
		viewLayout = new VerticalLayout();
		viewLayout.setSizeFull();

		windowHeader = createWindowHeader();
		viewLayout.addComponent(windowHeader);

		navigationBar = createNavigationBar();
		viewLayout.addComponent(navigationBar);

		viewContainer = createViewContainer();
		viewLayout.addComponent(viewContainer);
		viewLayout.setExpandRatio(viewContainer, 1.0F);

		createAndAddHomeView();
	}

	private WindowHeader createWindowHeader() {
		return new WindowHeader(getPresenter());
	}

	private NavigationBar createNavigationBar() {
		NavigationBar navigationBar = new NavigationBar();
		navigationBar.setViewController(getPresenter().getViewController());
		navigationBar.addStyleName("breadcrumbs");
		navigationBar.setWidth("100%");
		return navigationBar;
	}

	private ViewContainerComponent createViewContainer() {
		ViewContainerComponent viewContainer = new ViewContainerComponent();
		viewContainer.setViewController(getPresenter().getViewController());
		viewContainer.setSizeFull();
		return viewContainer;
	}

	private void createAndAddHomeView() {
		HomeViewImpl homeView = new HomeViewImpl(viewProvider);
		getPresenter().getViewController().goToView(homeView);
	}

	@Override
	public ComponentContainer getViewComponent() {
		return viewLayout;
	}

	@Override
	public void setNumberOfUnassignedTasks(long taskCount) {
		windowHeader.setNumberOfUnassignedTasks(taskCount);
	}

	@Override
	public void setNumberOfMyTasks(long taskCount) {
		windowHeader.setNumberOfMyTasks(taskCount);
	}

	@Override
	public void setNameOfCurrentUser(String username) {
		windowHeader.setNameOfCurrentUser(username);
	}

}
