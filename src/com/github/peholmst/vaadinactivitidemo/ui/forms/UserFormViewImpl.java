package com.github.peholmst.vaadinactivitidemo.ui.forms;


import com.github.peholmst.vaadinactivitidemo.ui.util.AbstractDemoView;
import com.github.peholmst.vaadinactivitidemo.ui.util.UserTaskForm;
import com.github.peholmst.vaadinactivitidemo.ui.util.UserTaskFormContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

public class UserFormViewImpl extends
		AbstractDemoView<UserFormView, UserFormPresenter> implements
		UserFormView {

	private static final long serialVersionUID = 3741153612537481922L;

	private final UserTaskFormContainer userTaskFormContainer;

	private Button submitButton;

	private VerticalLayout formContainerLayout;

	private UserTaskForm currentForm;

	public UserFormViewImpl(UserTaskFormContainer userTaskFormContainer) {
		this.userTaskFormContainer = userTaskFormContainer;
		init();
	}

	@SuppressWarnings("serial")
	@Override
	protected void initView() {
		super.initView();

		formContainerLayout = new VerticalLayout();
		formContainerLayout.setSizeFull();
		getViewLayout().addComponent(formContainerLayout);
		getViewLayout().setExpandRatio(formContainerLayout, 1.0F);

		submitButton = new Button("Submit Form");
		submitButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentForm != null) {
					getPresenter().submitForm(currentForm);
				}
			}
		});
		getViewComponent().addComponent(submitButton);
		submitButton.setVisible(false);
	}

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
		updateControls();
	}

	@Override
	public void hideForm() {
		currentForm = null;
		updateControls();
	}

	private void updateControls() {
		updateHeaderLabel();
		submitButton.setVisible(currentForm != null);
		formContainerLayout.removeAllComponents();
		if (currentForm != null) {
			formContainerLayout.addComponent(currentForm.getFormComponent());
		}
	}
}
