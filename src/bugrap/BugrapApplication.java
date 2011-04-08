package bugrap;

import org.activiti.engine.ProcessEngines;

import bugrap.bpmn.forms.SubmitBugReportForm;
import bugrap.ui.forms.UserFormView;
import bugrap.ui.forms.UserFormViewImpl;
import bugrap.ui.identity.IdentityManagementView;
import bugrap.ui.identity.IdentityManagementViewImpl;
import bugrap.ui.login.LoginViewImpl;
import bugrap.ui.login.UserLoggedInEvent;
import bugrap.ui.main.MainViewImpl;
import bugrap.ui.main.UserLoggedOutEvent;
import bugrap.ui.processes.ProcessView;
import bugrap.ui.processes.ProcessViewImpl;
import bugrap.ui.tasks.MyTasksView;
import bugrap.ui.tasks.MyTasksViewImpl;
import bugrap.ui.tasks.UnassignedTasksView;
import bugrap.ui.tasks.UnassignedTasksViewImpl;
import bugrap.ui.util.UserTaskFormContainer;

import com.github.peholmst.mvp4vaadin.ViewEvent;
import com.github.peholmst.mvp4vaadin.ViewListener;
import com.github.peholmst.mvp4vaadin.navigation.DefaultViewProvider;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class BugrapApplication extends Application implements ViewListener {

	private static final long serialVersionUID = -9012126232401480280L;

	private LoginViewImpl loginView;

	private MainViewImpl mainView;

	private DefaultViewProvider viewProvider;

	private UserTaskFormContainer userTaskFormContainer;

	@Override
	public void init() {
		setTheme("BugrapActivitiDemo");
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
		viewProvider.addPreinitializedView(new ProcessViewImpl(this),
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
