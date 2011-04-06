package bugrap.util;

import java.util.Map;

import org.activiti.engine.form.FormData;

public interface UserTaskForm {

	String getFormKey();
	
	void populateForm(FormData formData);
	
	Map<String, String> getFormProperties();
	
}
