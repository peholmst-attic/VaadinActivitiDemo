package bugrap.ui.util;

import java.util.Map;

import org.activiti.engine.form.FormData;

import com.vaadin.ui.Component;

public interface UserTaskForm extends java.io.Serializable {

	String getDisplayName();

	String getDescription();

	String getFormKey();

	void populateForm(FormData formData);

	Map<String, String> getFormProperties();

	Component getFormComponent();
}
