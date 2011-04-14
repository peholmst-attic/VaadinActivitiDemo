package com.github.peholmst.vaadinactivitidemo.bpmn.forms;

import java.util.Map;


import com.github.peholmst.vaadinactivitidemo.ui.util.AbstractUserTaskForm;
import com.github.peholmst.vaadinactivitidemo.ui.util.UserTaskForm;
import com.vaadin.ui.TextField;

public class SubmitBugReportForm extends AbstractUserTaskForm implements
		UserTaskForm {

	private static final long serialVersionUID = -8675838686891790914L;

	public static final String FORM_KEY = "submitBugReportForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	@Override
	protected void init() {
		project = new TextField("Project");
		addComponent(project);

		version = new TextField("Version");
		addComponent(version);

		summary = new TextField("Summary");
		addComponent(summary);

	}

	@Override
	public String getDisplayName() {
		return "Submit Bug Report";
	}

	@Override
	public String getDescription() {
		return "Submit a new bug report";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("project", (String) project.getValue());
		destination.put("version", (String) version.getValue());
		destination.put("summary", (String) summary.getValue());
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("project")) {
			project.setValue(propertyValue);
		} else if (propertyId.equals("version")) {
			version.setValue(propertyValue);
		} else if (propertyId.equals("summary")) {
			summary.setValue(propertyValue);
		}
	}
}
