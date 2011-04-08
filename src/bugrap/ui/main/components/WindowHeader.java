package bugrap.ui.main.components;

import bugrap.ui.main.MainPresenter;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

public class WindowHeader extends HorizontalLayout implements RefreshListener {

	private static final long serialVersionUID = 2604699348129000392L;
	private Label currentUserLabel;
	private Button logoutButton;
	private Button myTasksButton;
	private Button unassignedTasksButton;
	private Refresher refresher;
	private MainPresenter mainPresenter;
	private long numberOfUnassignedTasks = -1;
	private long numberOfMyTasks = -1;

	public WindowHeader(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;

		setWidth("100%");
		setMargin(true);
		setSpacing(true);
		addStyleName(Reindeer.LAYOUT_BLACK);

		VerticalLayout title = createTitle();
		addComponent(title);
		setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		setExpandRatio(title, 1.0F);

		myTasksButton = createMyTasksButton();
		addComponent(myTasksButton);
		setComponentAlignment(myTasksButton, Alignment.MIDDLE_RIGHT);

		unassignedTasksButton = createUnassignedTasksButton();
		addComponent(unassignedTasksButton);
		setComponentAlignment(unassignedTasksButton, Alignment.MIDDLE_RIGHT);

		logoutButton = createLogoutButton();
		addComponent(logoutButton);
		setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);

		refresher = createRefresher();
		addComponent(refresher);
	}

	private VerticalLayout createTitle() {
		VerticalLayout layout = new VerticalLayout();

		Label appTitle = new Label("Bugrap Activiti Demo");
		appTitle.addStyleName(Reindeer.LABEL_H1);
		layout.addComponent(appTitle);

		currentUserLabel = new Label();
		layout.addComponent(currentUserLabel);

		return layout;
	}

	@SuppressWarnings("serial")
	private Button createMyTasksButton() {
		Button button = new Button();
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				mainPresenter.showMyTasks();
			}
		});
		button.addStyleName(Reindeer.BUTTON_SMALL);
		return button;
	}

	@SuppressWarnings("serial")
	private Button createUnassignedTasksButton() {
		Button button = new Button();
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				mainPresenter.showUnassignedTasks();
			}
		});
		button.addStyleName(Reindeer.BUTTON_SMALL);
		return button;
	}

	@SuppressWarnings("serial")
	private Button createLogoutButton() {
		Button button = new Button("Logout");
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				mainPresenter.logout();
			}
		});
		button.addStyleName(Reindeer.BUTTON_SMALL);
		return button;
	}

	private Refresher createRefresher() {
		Refresher refresher = new Refresher();
		refresher.addListener(this);
		return refresher;
	}

	public void setNumberOfUnassignedTasks(long taskCount) {
		unassignedTasksButton.setCaption(String.format("Unassigned tasks (%d)",
				taskCount));
		if (numberOfUnassignedTasks > -1 && numberOfUnassignedTasks < taskCount) {
			getWindow().showNotification("There are new unassigned tasks",
					Notification.TYPE_TRAY_NOTIFICATION);
		}
		numberOfUnassignedTasks = taskCount;
	}

	public void setNumberOfMyTasks(long taskCount) {
		myTasksButton.setCaption(String.format("My tasks (%d)", taskCount));
		if (numberOfMyTasks > -1 && numberOfMyTasks < taskCount) {
			getWindow().showNotification("You have new tasks",
					Notification.TYPE_TRAY_NOTIFICATION);
		}
		numberOfMyTasks = taskCount;
	}

	public void setNameOfCurrentUser(String username) {
		currentUserLabel.setContentMode(Label.CONTENT_XHTML);
		currentUserLabel.setValue(String.format(
				"Currently logged in as <b>%s</b>", username));
	}

	@Override
	public void refresh(Refresher source) {
		mainPresenter.refreshTaskCounters();
	}
}
