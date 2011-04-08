package bugrap.ui.tasks;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import com.vaadin.Application;

public class UnassignedTasksPresenter extends
		TasksPresenter<UnassignedTasksView> {

	private static final long serialVersionUID = -7000703160639810245L;

	private static Logger log = Logger.getLogger(UnassignedTasksPresenter.class
			.getName());

	public UnassignedTasksPresenter(UnassignedTasksView view,
			Application application) {
		super(view, application);
	}

	public void assignTaskToCurrentUser(Task task) {
		String currentUserId = getIdOfCurrentUser();

		log.log(Level.INFO, "Assigning task {1} to user {2}", new Object[] {
				task.getId(), currentUserId });
		try {
			getTaskService().claim(task.getId(), currentUserId);
			updateTaskList();
			getView().showTaskAssignmentSuccess(task);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not assign task to user", e);
			getView().showTaskAssignmentFailure(task);
		}
	}

	public void assignTaskToOtherUser(Task task) {
		// TODO Implement me!
	}

	@Override
	protected List<Task> queryForTasksToShow() {
		String currentUser = getIdOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		query.taskUnnassigned().taskCandidateUser(currentUser)
				.orderByTaskPriority().desc().orderByDueDate().desc();
		return query.list();
	}
}
