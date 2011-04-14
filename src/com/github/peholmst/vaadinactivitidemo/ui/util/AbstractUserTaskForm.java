package com.github.peholmst.vaadinactivitidemo.ui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractUserTaskForm extends VerticalLayout implements
		UserTaskForm {

	private static Logger log = Logger.getLogger(AbstractUserTaskForm.class
			.getName());

	private static final long serialVersionUID = -1578037829262126003L;

	private String processDefinitionId;

	private String taskId;

	public AbstractUserTaskForm() {
		init();
	}

	protected abstract void init();

	@Override
	public Type getFormType() {
		if (taskId != null) {
			return Type.TASK_FORM;
		} else if (processDefinitionId != null) {
			return Type.START_FORM;
		} else {
			throw new IllegalStateException(
					"No taskId nor processDefinitionId has been specified");
		}
	}

	@Override
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public void populateForm(StartFormData formData, String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
		populateFormFields(formData);
	}

	@Override
	public void populateForm(TaskFormData formData, String taskId) {
		this.taskId = taskId;
		populateFormFields(formData);
	}

	protected void populateFormFields(FormData formData) {
		log.info("Populating form fields");
		for (FormProperty property : formData.getFormProperties()) {
			String propertyId = property.getId();
			String propertyValue = property.getValue();
			log.log(Level.INFO, "Populating form field {1} with value {2}",
					new Object[] { propertyId, propertyValue });
			populateFormField(propertyId, propertyValue);
		}
	}

	@Override
	public Map<String, String> getFormProperties() {
		log.log(Level.INFO, "Constructing form property map for {1}",
				getFormKey());
		HashMap<String, String> map = new HashMap<String, String>();
		copyFormProperties(map);
		return map;
	}

	protected abstract void copyFormProperties(Map<String, String> destination);

	protected abstract void populateFormField(String propertyId,
			String propertyValue);

	@Override
	public Component getFormComponent() {
		return this;
	}

}
