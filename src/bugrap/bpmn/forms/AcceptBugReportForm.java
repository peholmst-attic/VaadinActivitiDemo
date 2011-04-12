package bugrap.bpmn.forms;

import java.util.Map;

import bugrap.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.TextField;

public class AcceptBugReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355792599279114870L;

	public static final String FORM_KEY = "acceptBugReportForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	private TextField targetVersion;

	private TextField priority;

	private TextField developer;

	@Override
	public String getDisplayName() {
		return "Accept Bug Report";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("developer", (String) developer.getValue());
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
		addComponent(developer);
	}

}
