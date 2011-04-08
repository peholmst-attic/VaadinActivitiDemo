package bugrap.ui.tasks;

import org.activiti.engine.task.Task;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

public class UnassignedTasksViewImpl extends
		AbstractTasksViewImpl<UnassignedTasksView, UnassignedTasksPresenter>
		implements UnassignedTasksView {

	private static final long serialVersionUID = -5116546065679650863L;

	public UnassignedTasksViewImpl(Application application) {
		super(application);
	}

	@Override
	public String getDisplayName() {
		return "Unassigned Tasks";
	}

	@Override
	public String getDescription() {
		return "Pending tasks that have not been assigned to anyone yet";
	}

	@Override
	protected UnassignedTasksPresenter createPresenter() {
		return new UnassignedTasksPresenter(this, getApplication());
	}

	@Override
	public void showTaskAssignmentSuccess(Task task) {
		getViewLayout().getWindow().showNotification(
				String.format("%s assigned successfully", task.getName()),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	@Override
	public void showTaskAssignmentFailure(Task task) {
		getViewLayout()
				.getWindow()
				.showNotification(
						String.format(
								"Could not assign %s. Please check the logs for more information.",
								task.getName()),
						Notification.TYPE_ERROR_MESSAGE);
	}

	@SuppressWarnings("serial")
	@Override
	protected PopupView createTaskPopup(final Task task) {
		final VerticalLayout layout = new VerticalLayout();
		final PopupView popup = new PopupView(task.getName(), layout);

		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label header = new Label(String.format(
				"What would you like to do with <b>%s</b>?", task.getName()));
		header.setContentMode(Label.CONTENT_XHTML);
		layout.addComponent(header);

		Button assignToMeButton = new Button("Assign to me");
		assignToMeButton.addStyleName(Reindeer.BUTTON_SMALL);
		assignToMeButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().assignTaskToCurrentUser(task);
				popup.setPopupVisible(false);
			}
		});
		layout.addComponent(assignToMeButton);

		Button assignToOtherButton = new Button("Assign to other user...");
		assignToOtherButton.addStyleName(Reindeer.BUTTON_SMALL);
		// TODO Add listener
		layout.addComponent(assignToOtherButton);
		return popup;
	}

	@Override
	protected String[] getVisibleColumns() {
		return new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" };
	}

}
