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

public class MyTasksViewImpl extends
		AbstractTasksViewImpl<MyTasksView, MyTasksPresenter> implements
		MyTasksView {

	public MyTasksViewImpl(Application application) {
		super(application);
	}

	private static final long serialVersionUID = 3414505813581248536L;

	@Override
	public String getDisplayName() {
		return "My Tasks";
	}

	@Override
	public String getDescription() {
		return "Pending tasks that have been assigned to me";
	}

	@Override
	protected MyTasksPresenter createPresenter() {
		return new MyTasksPresenter(this, getApplication());
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

		if (getPresenter().taskHasForm(task)) {
			Button openFormButton = new Button("Open Form");
			openFormButton.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					getPresenter().openFormForTask(task);
					popup.setPopupVisible(false);
				}
			});
			openFormButton.addStyleName(Reindeer.BUTTON_SMALL);
			layout.addComponent(openFormButton);
		} else {
			Button completeButton = new Button("Complete");
			completeButton.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					getPresenter().completeTask(task);
					popup.setPopupVisible(false);
				}
			});
			completeButton.addStyleName(Reindeer.BUTTON_SMALL);
			layout.addComponent(completeButton);
		}

		Button delegateToOtherUserButton = new Button(
				"Delegate to other user...");
		delegateToOtherUserButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().delegateToOtherUser(task);
				popup.setPopupVisible(false);
			}
		});
		delegateToOtherUserButton.addStyleName(Reindeer.BUTTON_SMALL);
		layout.addComponent(delegateToOtherUserButton);

		return popup;
	}

	@Override
	protected String[] getVisibleColumns() {
		return new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime", "assignee" };
	}

	@Override
	public void showTaskCompletedSuccess(Task task) {
		getViewLayout().getWindow().showNotification(
				String.format("%s completed successfully", task.getName()),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	@Override
	public void showTaskCompletedFailure(Task task) {
		getViewLayout()
				.getWindow()
				.showNotification(
						String.format(
								"Could not complete %s. Please check the logs for more information.",
								task.getName()),
						Notification.TYPE_ERROR_MESSAGE);
	}

}
