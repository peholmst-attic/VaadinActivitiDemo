package bugrap.ui.main;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.TaskQuery;

import com.github.peholmst.mvp4vaadin.Presenter;
import com.vaadin.Application;

public class MainPresenter extends Presenter<MainView> {

	private static final long serialVersionUID = 1767368195413660867L;

	private final Application application;

	public MainPresenter(MainView view, Application application) {
		super(view);
		this.application = application;
	}

	@Override
	public void init() {
		refreshTaskCounters();
		String currentUser = getNameOfCurrentUser();
		getView().setNameOfCurrentUser(currentUser);
	}

	public void logout() {
		fireViewEvent(new UserLoggedOutEvent(getView()));
	}

	public void refreshTaskCounters() {
		getView().setNumberOfMyTasks(getNumberOfTasksAssignedToCurrentUser());
		getView().setNumberOfUnassignedTasks(getNumberOfUnassignedTasks());
	}

	private long getNumberOfUnassignedTasks() {
		TaskQuery query = getTaskService().createTaskQuery();
		return query.taskUnnassigned().count();
	}

	private long getNumberOfTasksAssignedToCurrentUser() {
		String currentUser = getNameOfCurrentUser();
		TaskQuery query = getTaskService().createTaskQuery();
		return query.taskAssignee(currentUser).count();
	}

	public void showUnassignedTasks() {
		// TODO Implement me!
	}

	public void showMyTasks() {
		// TODO Implement me!
	}

	private String getNameOfCurrentUser() {
		String currentUserId = (String) application.getUser();
		User userInfo = getIdentityService().createUserQuery()
				.userId(currentUserId).singleResult();
		return String.format("%s %s", userInfo.getFirstName(),
				userInfo.getLastName());
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}

	private TaskService getTaskService() {
		return ProcessEngines.getDefaultProcessEngine().getTaskService();
	}

}
