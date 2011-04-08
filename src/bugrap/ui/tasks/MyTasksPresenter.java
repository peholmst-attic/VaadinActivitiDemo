package bugrap.ui.tasks;

import java.util.List;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import com.vaadin.Application;

public class MyTasksPresenter extends TasksPresenter<MyTasksView> {

	public MyTasksPresenter(MyTasksView view, Application application) {
		super(view, application);
	}

	private static final long serialVersionUID = 4095041737006335740L;

	@Override
	protected List<Task> queryForTasksToShow() {
		String currentUser = getIdOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		query.taskAssignee(currentUser).orderByTaskPriority().desc()
				.orderByDueDate().desc();
		return query.list();
	}

}
