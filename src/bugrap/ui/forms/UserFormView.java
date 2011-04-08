package bugrap.ui.forms;

import bugrap.ui.util.UserTaskForm;

import com.github.peholmst.mvp4vaadin.navigation.ControllableView;

public interface UserFormView extends ControllableView {

	String VIEW_ID = "userForm";

	String KEY_FORM_KEY = "formKey";

	String KEY_TASK_ID = "taskId";

	String KEY_PROCESS_DEFINITION_ID = "processDefinitionId";

	void setForm(UserTaskForm form);

	void hideForm();
}
