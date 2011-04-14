package com.github.peholmst.vaadinactivitidemo.ui.tasks;

import org.activiti.engine.task.Task;

public interface MyTasksView extends TasksView {

	String VIEW_ID = "myTasks";

	void showTaskCompletedSuccess(Task task);

	void showTaskCompletedFailure(Task task);
}
