package com.github.peholmst.vaadinactivitidemo;

import org.activiti.engine.ProcessEngines;

import com.github.peholmst.mvp4vaadin.ViewEvent;
import com.github.peholmst.mvp4vaadin.ViewListener;
import com.github.peholmst.mvp4vaadin.navigation.DefaultViewProvider;
import com.github.peholmst.vaadinactivitidemo.bpmn.forms.AcceptBugReportForm;
import com.github.peholmst.vaadinactivitidemo.bpmn.forms.ResolveBugReportForm;
import com.github.peholmst.vaadinactivitidemo.bpmn.forms.SubmitBugReportForm;
import com.github.peholmst.vaadinactivitidemo.bpmn.forms.UpdateBugReportForm;
import com.github.peholmst.vaadinactivitidemo.ui.forms.UserFormView;
import com.github.peholmst.vaadinactivitidemo.ui.forms.UserFormViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.identity.IdentityManagementView;
import com.github.peholmst.vaadinactivitidemo.ui.identity.IdentityManagementViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.login.LoginViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.login.UserLoggedInEvent;
import com.github.peholmst.vaadinactivitidemo.ui.main.MainViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.main.UserLoggedOutEvent;
import com.github.peholmst.vaadinactivitidemo.ui.processes.ProcessView;
import com.github.peholmst.vaadinactivitidemo.ui.processes.ProcessViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.MyTasksView;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.MyTasksViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.UnassignedTasksView;
import com.github.peholmst.vaadinactivitidemo.ui.tasks.UnassignedTasksViewImpl;
import com.github.peholmst.vaadinactivitidemo.ui.util.UserTaskFormContainer;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class DemoApplication extends Application implements ViewListener {

	private static final long serialVersionUID = -9012126232401480280L;

	private LoginViewImpl loginView;

	private MainViewImpl mainView;

	private DefaultViewProvider viewProvider;

	private UserTaskFormContainer userTaskFormContainer;

	@Override
	public void init() {
		setTheme("VaadinActivitiDemo");
		createAndShowLoginWindow();
	}

	private void createAndShowLoginWindow() {
		loginView = new LoginViewImpl();
		loginView.addListener(this);
		Window loginWindow = new Window(loginView.getDisplayName(),
				loginView.getViewComponent());
		setMainWindow(loginWindow);
	}

	private void createAndShowMainWindow() {
		createAndInitViewProvider();
		loginView.removeListener(this);
		mainView = new MainViewImpl(this, viewProvider);
		mainView.addListener(this);
		// Remove old login window
		removeWindow(getMainWindow());
		// Set new main window
		Window mainWindow = new Window(mainView.getDisplayName(),
				mainView.getViewComponent());
		setMainWindow(mainWindow);
	}

	private void createAndInitViewProvider() {
		createAndInitUserTaskFormContainer();
		viewProvider = new DefaultViewProvider();
		viewProvider.addPreinitializedView(new MyTasksViewImpl(this),
				MyTasksView.VIEW_ID);
		viewProvider.addPreinitializedView(new UnassignedTasksViewImpl(this),
				UnassignedTasksView.VIEW_ID);
		viewProvider.addPreinitializedView(new ProcessViewImpl(),
				ProcessView.VIEW_ID);
		viewProvider.addPreinitializedView(new IdentityManagementViewImpl(),
				IdentityManagementView.VIEW_ID);
		viewProvider.addPreinitializedView(new UserFormViewImpl(
				userTaskFormContainer), UserFormView.VIEW_ID);
	}

	private void createAndInitUserTaskFormContainer() {
		userTaskFormContainer = new UserTaskFormContainer();
		userTaskFormContainer.registerForm(SubmitBugReportForm.FORM_KEY,
				SubmitBugReportForm.class);
		userTaskFormContainer.registerForm(UpdateBugReportForm.FORM_KEY,
				UpdateBugReportForm.class);
		userTaskFormContainer.registerForm(AcceptBugReportForm.FORM_KEY,
				AcceptBugReportForm.class);
		userTaskFormContainer.registerForm(ResolveBugReportForm.FORM_KEY,
				ResolveBugReportForm.class);
	}

	@Override
	public void close() {
		ProcessEngines.getDefaultProcessEngine().getIdentityService()
				.setAuthenticatedUserId(null);
		super.close();
	}

	@Override
	public void handleViewEvent(ViewEvent event) {
		if (event instanceof UserLoggedInEvent) {
			String username = ((UserLoggedInEvent) event).getUsername();
			setUser(username);
			createAndShowMainWindow();
		} else if (event instanceof UserLoggedOutEvent) {
			close();
		}
	}

}
