package bugrap.ui.tasks;

import org.activiti.engine.task.Task;

public interface UnassignedTasksView extends TasksView {

	String VIEW_ID = "unassignedTasks";

	void showTaskAssignmentSuccess(Task task);

	void showTaskAssignmentFailure(Task task);
}
