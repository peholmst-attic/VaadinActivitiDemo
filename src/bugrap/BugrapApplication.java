package bugrap;

import bugrap.main.MainWindow;

import com.vaadin.Application;

public class BugrapApplication extends Application {

	private static final long serialVersionUID = -9012126232401480280L;

	@Override
	public void init() {
		MainWindow mainWindow = new MainWindow();
		setMainWindow(mainWindow);
	}

}
