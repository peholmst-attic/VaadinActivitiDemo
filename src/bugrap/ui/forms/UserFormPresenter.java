package bugrap.ui.forms;

import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.form.FormData;

import bugrap.ui.util.UserTaskForm;
import bugrap.ui.util.UserTaskFormContainer;

import com.github.peholmst.mvp4vaadin.navigation.ControllablePresenter;
import com.github.peholmst.mvp4vaadin.navigation.ControllableView;
import com.github.peholmst.mvp4vaadin.navigation.Direction;
import com.github.peholmst.mvp4vaadin.navigation.ViewController;

public class UserFormPresenter extends ControllablePresenter<UserFormView> {

	private static final long serialVersionUID = 6378990821642431252L;

	private final UserTaskFormContainer userTaskFormContainer;

	public UserFormPresenter(UserFormView view,
			UserTaskFormContainer userTaskFormContainer) {
		super(view);
		this.userTaskFormContainer = userTaskFormContainer;
	}

	@Override
	protected void viewShown(ViewController viewController,
			Map<String, Object> userData, ControllableView oldView,
			Direction direction) {
		if (userData != null) {
			String formKey = (String) userData.get(UserFormView.KEY_FORM_KEY);
			if (userData.containsKey(UserFormView.KEY_TASK_ID)) {
				String taskId = (String) userData.get(UserFormView.KEY_TASK_ID);
				showTaskForm(formKey, taskId);
			} else if (userData
					.containsKey(UserFormView.KEY_PROCESS_DEFINITION_ID)) {
				String processDefId = (String) userData
						.get(UserFormView.KEY_PROCESS_DEFINITION_ID);
				showProcessForm(formKey, processDefId);
			} else {
				getView().hideForm();
			}
		} else {
			getView().hideForm();
		}
	}

	public void submitForm(UserTaskForm form) {

	}

	private void showTaskForm(String formKey, String taskId) {
		UserTaskForm form = userTaskFormContainer.getForm(formKey);
		FormData formData = getFormService().getTaskFormData(taskId);
		form.populateForm(formData);
		getView().setForm(form);
	}

	private void showProcessForm(String formKey, String processDefinitionId) {
		UserTaskForm form = userTaskFormContainer.getForm(formKey);
		FormData formData = getFormService().getStartFormData(
				processDefinitionId);
		form.populateForm(formData);
		getView().setForm(form);
	}

	private FormService getFormService() {
		return ProcessEngines.getDefaultProcessEngine().getFormService();
	}
}
