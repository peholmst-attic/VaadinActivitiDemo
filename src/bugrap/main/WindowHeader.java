package bugrap.main;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

public class WindowHeader extends HorizontalLayout {

	private static final long serialVersionUID = 2604699348129000392L;
	private Button logoutButton;

	public WindowHeader() {
		setWidth("100%");
		setMargin(true);
		setSpacing(true);
		addStyleName(Reindeer.LAYOUT_BLACK);

		Label appTitle = new Label("Bugrap Activiti Demo");
		appTitle.addStyleName(Reindeer.LABEL_H1);
		addComponent(appTitle);
		setComponentAlignment(appTitle, Alignment.MIDDLE_LEFT);
		setExpandRatio(appTitle, 1.0f);

		logoutButton = new Button("Logout", new Button.ClickListener() {

			private static final long serialVersionUID = 1790884371509033663L;

			@Override
			public void buttonClick(ClickEvent event) {
				getApplication().close();
			}
		});
		logoutButton.addStyleName(Reindeer.BUTTON_SMALL);
		addComponent(logoutButton);
		setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
	}
}
