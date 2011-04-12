package bugrap.bpmn.forms;

import java.util.Map;

import bugrap.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class UpdateBugReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -2276858423389030480L;

	public static final String FORM_KEY = "updateBugReportForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	private TextField targetVersion;

	private ComboBox priority;

	@Override
	public String getDisplayName() {
		return "Update Bug Report";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("targetVersion", (String) targetVersion.getValue());
		destination.put("priority", (String) priority.getValue());
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
		addComponent(targetVersion);

		priority = new ComboBox("Priority");
		priority.addItem("High");
		priority.addItem("Medium");
		priority.addItem("Low");
		addComponent(priority);
	}

}
