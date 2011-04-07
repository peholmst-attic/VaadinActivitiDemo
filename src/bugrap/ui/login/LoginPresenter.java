package bugrap.ui.login;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;

import com.github.peholmst.mvp4vaadin.Presenter;

public class LoginPresenter extends Presenter<LoginView> {

	private static final long serialVersionUID = -2879324628084421626L;

	public LoginPresenter(LoginView view) {
		super(view);
	}

	public void attemptLogin(String username, String password) {
		if (getIdentityService().checkPassword(username, password)) {
			getIdentityService().setAuthenticatedUserId(username);
			fireViewEvent(new UserLoggedInEvent(getView(), username));
		} else {
			getView().clearForm();
			getView().showLoginFailed();
		}
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}
}
