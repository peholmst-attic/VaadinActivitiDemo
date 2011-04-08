package bugrap.ui.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import bugrap.ui.forms.UserFormView;

import com.vaadin.Application;

public class MyTasksPresenter extends TasksPresenter<MyTasksView> {

	private static final long serialVersionUID = 4095041737006335740L;

	private static Logger log = Logger.getLogger(MyTasksPresenter.class
			.getName());

	public MyTasksPresenter(MyTasksView view, Application application) {
		super(view, application);
	}

	@Override
	protected List<Task> queryForTasksToShow() {
		String currentUser = getIdOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		query.taskAssignee(currentUser).orderByTaskPriority().desc()
				.orderByDueDate().desc();
		return query.list();
	}

	public void completeTask(Task task) {
		log.log(Level.INFO, "Completing task {1}", task.getId());
		try {
			getTaskService().complete(task.getId());
			updateTaskList();
			getView().showTaskCompletedSuccess(task);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not complete task", e);
			getView().showTaskCompletedFailure(task);
		}
	}

	public boolean taskHasForm(Task task) {
		return getFormService().getTaskFormData(task.getId()).getFormKey() != null;
	}

	public void openFormForTask(Task task) {
		String formKey = getFormKey(task);
		if (formKey != null) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(UserFormView.KEY_FORM_KEY, formKey);
			params.put(UserFormView.KEY_TASK_ID, task.getId());
			getViewController().goToView(UserFormView.VIEW_ID, params);
		}
	}

	private String getFormKey(Task task) {
		return getFormService().getTaskFormData(task.getId()).getFormKey();
	}

	public void delegateToOtherUser(Task task) {

	}

	private FormService getFormService() {
		return ProcessEngines.getDefaultProcessEngine().getFormService();
	}
}
