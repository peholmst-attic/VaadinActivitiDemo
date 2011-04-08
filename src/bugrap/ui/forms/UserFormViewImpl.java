package bugrap.ui.forms;

import bugrap.ui.util.AbstractBugrapView;
import bugrap.ui.util.UserTaskForm;
import bugrap.ui.util.UserTaskFormContainer;

public class UserFormViewImpl extends
		AbstractBugrapView<UserFormView, UserFormPresenter> implements
		UserFormView {

	private static final long serialVersionUID = 3741153612537481922L;

	private final UserTaskFormContainer userTaskFormContainer;

	public UserFormViewImpl(UserTaskFormContainer userTaskFormContainer) {
		this.userTaskFormContainer = userTaskFormContainer;
		init();
	}

	private UserTaskForm currentForm;

	@Override
	public String getDisplayName() {
		return currentForm != null ? currentForm.getDisplayName()
				: "No form available";
	}

	@Override
	public String getDescription() {
		return currentForm != null ? currentForm.getDescription()
				: "There is no form to show";
	}

	@Override
	protected UserFormPresenter createPresenter() {
		return new UserFormPresenter(this, userTaskFormContainer);
	}

	@Override
	public void setForm(UserTaskForm form) {
		currentForm = form;
		updateHeaderLabel();
	}

	@Override
	public void hideForm() {
		currentForm = null;
	}

}
