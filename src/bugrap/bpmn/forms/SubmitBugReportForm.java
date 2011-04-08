package bugrap.bpmn.forms;

import java.util.Map;

import org.activiti.engine.form.FormData;

import bugrap.ui.util.UserTaskForm;

import com.vaadin.ui.Component;
import com.vaadin.ui.Form;

public class SubmitBugReportForm extends Form implements UserTaskForm {

	private static final long serialVersionUID = -8675838686891790914L;

	public static final String FORM_KEY = "submitBugReportForm";

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
	public void populateForm(FormData formData) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getFormProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getFormComponent() {
		return this;
	}

}
