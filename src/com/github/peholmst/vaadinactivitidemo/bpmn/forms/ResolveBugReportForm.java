package com.github.peholmst.vaadinactivitidemo.bpmn.forms;

import java.util.Map;


import com.github.peholmst.vaadinactivitidemo.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class ResolveBugReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -5179047053962042352L;

	public static final String FORM_KEY = "resolveBugReportForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	private TextField targetVersion;

	private TextField priority;

	private TextField developer;

	private ComboBox resolution;

	private TextField comments;

	@Override
	public String getDisplayName() {
		return "Resolve Bug Report";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("resolution", (String) resolution.getValue());
		destination.put("comments", (String) comments.getValue());
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("project")) {
			project.setValue(propertyValue);
		} else if (propertyId.equals("version")) {
			version.setValue(propertyValue);
		} else if (propertyId.equals("summary")) {
			summary.setValue(propertyValue);
		} else if (propertyId.equals("targetVersion")) {
			targetVersion.setValue(propertyValue);
		} else if (propertyId.equals("priority")) {
			priority.setValue(propertyValue);
		} else if (propertyId.equals("developer")) {
			developer.setValue(propertyValue);
		} else if (propertyId.equals("resolution")) {
			resolution.setValue(propertyValue);
		} else if (propertyId.equals("comments")) {
			comments.setValue(propertyValue);
		}
	}

	@Override
	protected void init() {
		project = new TextField("Project");
		project.setEnabled(false);
		addComponent(project);

		version = new TextField("Version");
		version.setEnabled(false);
		addComponent(version);

		summary = new TextField("Summary");
		summary.setEnabled(false);
		addComponent(summary);

		targetVersion = new TextField("Target Version");
		targetVersion.setEnabled(false);
		addComponent(targetVersion);

		priority = new TextField("Priority");
		priority.setEnabled(false);
		addComponent(priority);

		developer = new TextField("Developer");
		developer.setEnabled(false);
		addComponent(developer);

		resolution = new ComboBox("Resolution");
		resolution.addItem("Fixed");
		resolution.addItem("Duplicate");
		resolution.addItem("Won't Fix");
		resolution.addItem("Postponed");
		addComponent(resolution);

		comments = new TextField("Comments");
		addComponent(comments);
	}
}
