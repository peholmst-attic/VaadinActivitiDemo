package bugrap.ui.processes;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.github.peholmst.mvp4vaadin.VaadinView;
import com.github.peholmst.mvp4vaadin.navigation.AbstractControllableView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

public class ProcessViewImpl extends
		AbstractControllableView<ProcessView, ProcessPresenter> implements
		ProcessView, VaadinView {

	private static final long serialVersionUID = 8609961536309155685L;

	private VerticalLayout viewLayout;

	private Table processTable;

	private BeanItemContainer<ProcessDefinition> dataSource;

	public ProcessViewImpl() {
		super(true);
	}

	@Override
	public String getDisplayName() {
		return "Process Browser";
	}

	@Override
	public String getDescription() {
		return "Browse processes and start new instances";
	}

	@Override
	protected ProcessPresenter createPresenter() {
		return new ProcessPresenter(this);
	}

	@Override
	public ComponentContainer getViewComponent() {
		return viewLayout;
	}

	@Override
	protected void initView() {
		viewLayout = new VerticalLayout();
		viewLayout.setSizeFull();
		viewLayout.setMargin(true);
		viewLayout.setSpacing(true);

		Label header = new Label(getDisplayName());
		header.addStyleName(Reindeer.LABEL_H1);
		viewLayout.addComponent(header);

		processTable = new Table();
		dataSource = new BeanItemContainer<ProcessDefinition>(
				ProcessDefinition.class);
		processTable.setContainerDataSource(dataSource);
		processTable.setVisibleColumns(new String[] { "name", "key", "version",
				"resourceName", "category" });
		processTable.setSizeFull();
		processTable.addGeneratedColumn("name", createNameColumnGenerator());
		viewLayout.addComponent(processTable);
		viewLayout.setExpandRatio(processTable, 1.0F);
	}

	@SuppressWarnings("serial")
	private ColumnGenerator createNameColumnGenerator() {
		return new ColumnGenerator() {

			@Override
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				ProcessDefinition processDefinition = (ProcessDefinition) itemId;
				PopupView popupView = createProcessDefinitionPopup(processDefinition);
				return popupView;
			}
		};
	}

	@SuppressWarnings("serial")
	private PopupView createProcessDefinitionPopup(
			final ProcessDefinition processDefinition) {
		final VerticalLayout layout = new VerticalLayout();
		final PopupView popup = new PopupView(processDefinition.getName(),
				layout);

		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label header = new Label(String.format(
				"What would you like to do with %s?",
				processDefinition.getName()));
		layout.addComponent(header);

		Button startNewInstanceButton = new Button("Start a new instance");
		startNewInstanceButton.addStyleName(Reindeer.BUTTON_SMALL);
		startNewInstanceButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().startNewInstance(processDefinition);
				popup.setPopupVisible(false);
			}
		});
		layout.addComponent(startNewInstanceButton);

		// TODO Add start form support

		/*
		 * if (processDefinition.hasStartFormKey()) { Button
		 * startNewInstanceWithVariablesButton = new Button(
		 * "Start a new instance and fill in the Start Form");
		 * startNewInstanceWithVariablesButton
		 * .addStyleName(Reindeer.BUTTON_SMALL);
		 * layout.addComponent(startNewInstanceWithVariablesButton); }
		 */

		return popup;
	}

	@Override
	public void setProcessDefinitions(List<ProcessDefinition> definitions) {
		dataSource.removeAllItems();
		dataSource.addAll(definitions);
	}

	@Override
	public void showProcessStartSuccess(ProcessDefinition process) {
		viewLayout.getWindow().showNotification(
				String.format("%s started successfully", process.getName()),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	@Override
	public void showProcessStartFailure(ProcessDefinition process) {
		viewLayout
				.getWindow()
				.showNotification(
						String.format(
								"Could not start %s. Please check the logs for more information.",
								process.getName()),
						Notification.TYPE_ERROR_MESSAGE);

	}

}
