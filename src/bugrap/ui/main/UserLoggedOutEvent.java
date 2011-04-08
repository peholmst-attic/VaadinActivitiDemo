package bugrap.ui.main;

import com.github.peholmst.mvp4vaadin.View;
import com.github.peholmst.mvp4vaadin.ViewEvent;

public class UserLoggedOutEvent extends ViewEvent {

	private static final long serialVersionUID = -4491077705486724571L;

	public UserLoggedOutEvent(View source) {
		super(source);
	}

}
