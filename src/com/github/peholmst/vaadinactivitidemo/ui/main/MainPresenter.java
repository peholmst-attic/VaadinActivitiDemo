package com.github.peholmst.vaadinactivitidemo.ui.main;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.TaskQuery;


import com.github.peholmst.mvp4vaadin.Presenter;
import com.github.peholmst.mvp4vaadin.navigation.DefaultViewController;
import com.github.peholmst.mvp4vaadin.navigation.ViewController;
import com.github.peholmst.mvp4vaadin.navigation.ViewProvider;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.MyTasksView;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.UnassignedTasksView;
import com.vaadin.Application;

public class MainPresenter extends Presenter<MainView> {

	private static final long serialVersionUID = 1767368195413660867L;

	private final Application application;

	private ViewController viewController;

	public MainPresenter(MainView view, Application application,
			ViewProvider viewProvider) {
		super(view);
		this.application = application;
		viewController = new DefaultViewController();
		viewController.setViewProvider(viewProvider);
	}

	@Override
	public void init() {
		refreshTaskCounters();
		String currentUser = getNameOfCurrentUser();
		getView().setNameOfCurrentUser(currentUser);
	}

	public ViewController getViewController() {
		return viewController;
	}

	public void logout() {
		fireViewEvent(new UserLoggedOutEvent(getView()));
	}

	public void refreshTaskCounters() {
		getView().setNumberOfMyTasks(getNumberOfTasksAssignedToCurrentUser());
		getView().setNumberOfUnassignedTasks(getNumberOfUnassignedTasks());
	}

	private long getNumberOfUnassignedTasks() {
		String currentUser = getIdOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		return query.taskUnnassigned().taskCandidateUser(currentUser).count();
	}

	private long getNumberOfTasksAssignedToCurrentUser() {
		String currentUser = getIdOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		return query.taskAssignee(currentUser).count();
	}

	public void showUnassignedTasks() {
		getViewController().goToView(UnassignedTasksView.VIEW_ID);
	}

	public void showMyTasks() {
		getViewController().goToView(MyTasksView.VIEW_ID);
	}

	private String getNameOfCurrentUser() {
		String currentUserId = getIdOfCurrentUser();
		User userInfo = getIdentityService().createUserQuery()
				.userId(currentUserId).singleResult();
		return String.format("%s %s", userInfo.getFirstName(),
				userInfo.getLastName());
	}

	private String getIdOfCurrentUser() {
		return (String) application.getUser();
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}

	private TaskService getTaskService() {
		return ProcessEngines.getDefaultProcessEngine().getTaskService();
	}

}
