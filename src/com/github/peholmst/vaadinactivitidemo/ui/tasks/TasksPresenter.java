package com.github.peholmst.vaadinactivitidemo.ui.tasks;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import com.github.peholmst.mvp4vaadin.navigation.ControllablePresenter;
import com.github.peholmst.mvp4vaadin.navigation.ControllableView;
import com.github.peholmst.mvp4vaadin.navigation.Direction;
import com.github.peholmst.mvp4vaadin.navigation.ViewController;
import com.vaadin.Application;

public abstract class TasksPresenter<V extends TasksView> extends
		ControllablePresenter<V> {

	private static final long serialVersionUID = -7145661950408374225L;

	private final Application application;

	public TasksPresenter(V view, Application application) {
		super(view);
		this.application = application;
	}

	protected final Application getApplication() {
		return application;
	}

	protected abstract List<Task> queryForTasksToShow();

	@Override
	protected void viewShown(ViewController viewController,
			Map<String, Object> userData, ControllableView oldView,
			Direction direction) {
		updateTaskList();
	}

	protected void updateTaskList() {
		List<Task> tasksToShow = queryForTasksToShow();
		getView().setTasks(tasksToShow);
	}

	public void refreshTasks() {
		updateTaskList();
	}

	protected String getIdOfCurrentUser() {
		return (String) getApplication().getUser();
	}

	protected TaskService getTaskService() {
		return ProcessEngines.getDefaultProcessEngine().getTaskService();
	}
}
