package bugrap;

import org.activiti.engine.ProcessEngines;

import bugrap.ui.login.LoginViewImpl;
import bugrap.ui.login.UserLoggedInEvent;
import bugrap.ui.main.MainViewImpl;
import bugrap.ui.main.UserLoggedOutEvent;

import com.github.peholmst.mvp4vaadin.ViewEvent;
import com.github.peholmst.mvp4vaadin.ViewListener;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class BugrapApplication extends Application implements ViewListener {

	private static final long serialVersionUID = -9012126232401480280L;

	private LoginViewImpl loginView;

	private MainViewImpl mainView;

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
		loginView.removeListener(this);
		mainView = new MainViewImpl(this);
		mainView.addListener(this);
		// Remove old login window
		removeWindow(getMainWindow());
		// Set new main window
		Window mainWindow = new Window(mainView.getDisplayName(),
				mainView.getViewComponent());
		setMainWindow(mainWindow);
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
