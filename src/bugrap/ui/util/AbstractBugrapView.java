package bugrap.ui.util;

import com.github.peholmst.mvp4vaadin.VaadinView;
import com.github.peholmst.mvp4vaadin.navigation.AbstractControllableView;
import com.github.peholmst.mvp4vaadin.navigation.ControllablePresenter;
import com.github.peholmst.mvp4vaadin.navigation.ControllableView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public abstract class AbstractBugrapView<V extends ControllableView, P extends ControllablePresenter<V>>
		extends AbstractControllableView<V, P> implements VaadinView {

	private static final long serialVersionUID = -6771902313288610021L;

	private VerticalLayout viewLayout;

	public AbstractBugrapView() {
	}

	public AbstractBugrapView(boolean init) {
		super(init);
	}

	@Override
	public ComponentContainer getViewComponent() {
		return viewLayout;
	}

	protected final VerticalLayout getViewLayout() {
		return viewLayout;
	}

	@SuppressWarnings("serial")
	@Override
	protected void initView() {
		viewLayout = new VerticalLayout();
		viewLayout.setSizeFull();
		viewLayout.setMargin(true);
		viewLayout.setSpacing(true);

		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setSpacing(true);
		viewLayout.addComponent(headerLayout);

		Label header = new Label(getDisplayName());
		header.addStyleName(Reindeer.LABEL_H1);
		headerLayout.addComponent(header);
		headerLayout.setComponentAlignment(header, Alignment.MIDDLE_LEFT);
		headerLayout.setExpandRatio(header, 1.0F);

		addAdditionalControlsToHeader(headerLayout);

		Button backButton = new Button("« Go Back");
		backButton.addStyleName(Reindeer.BUTTON_SMALL);
		backButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getViewController().goBack();
			}
		});
		headerLayout.addComponent(backButton);
		headerLayout.setComponentAlignment(backButton, Alignment.MIDDLE_RIGHT);
	}

	protected void addAdditionalControlsToHeader(HorizontalLayout headerLayout) {

	}
}
