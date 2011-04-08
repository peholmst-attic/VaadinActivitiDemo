package bugrap.ui.tasks;

import org.activiti.engine.task.Task;

import com.vaadin.Application;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

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

	@Override
	protected PopupView createTaskPopup(Task task) {
		final VerticalLayout layout = new VerticalLayout();
		final PopupView popup = new PopupView(task.getName(), layout);
		// TODO Complete me!
		return popup;
	}

	@Override
	protected String[] getVisibleColumns() {
		return new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime", "assignee" };
	}

}
