package com.github.peholmst.vaadinactivitidemo.ui.tasks;

import java.util.List;

import org.activiti.engine.task.Task;

import com.github.peholmst.mvp4vaadin.navigation.ControllableView;

public interface TasksView extends ControllableView {

	void setTasks(List<Task> tasks);

}
