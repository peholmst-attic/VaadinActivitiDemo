package com.github.peholmst.vaadinactivitidemo.ui.util;

import java.util.Map;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;

import com.vaadin.ui.Component;

public interface UserTaskForm extends java.io.Serializable {

	enum Type {
		START_FORM, TASK_FORM
	}

	Type getFormType();

	String getProcessDefinitionId();

	String getTaskId();

	String getDisplayName();

	String getDescription();

	String getFormKey();

	void populateForm(StartFormData formData, String processDefinitionId);

	void populateForm(TaskFormData formData, String taskId);

	Map<String, String> getFormProperties();

	Component getFormComponent();
}
