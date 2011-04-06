package bugrap.processes;

import java.util.List;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

public class ProcessView extends VerticalLayout {

	private static final long serialVersionUID = -3877653719909170186L;

	private Table processTable;

	public ProcessView() {
		initComponents();
	}

	private void initComponents() {
		setSizeFull();
		setMargin(true);
		processTable = new Table();
		BeanItemContainer<ProcessDefinition> dataSource = new BeanItemContainer<ProcessDefinition>(
				ProcessDefinition.class, getAllProcessDefinitions());
		processTable.setContainerDataSource(dataSource);
		processTable.setVisibleColumns(new String[] { "name", "key", "version",
				"resourceName", "category" });
		processTable.setSizeFull();
		processTable.addGeneratedColumn("name", createNameColumnGenerator());
		addComponent(processTable);
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
				try {
					getRuntimeService().startProcessInstanceById(
							processDefinition.getId());
					getWindow().showNotification(
							String.format("%s started",
									processDefinition.getName()),
							Notification.TYPE_TRAY_NOTIFICATION);
				} catch (RuntimeException e) {
					getWindow().showNotification(
							String.format("Could not start process: %s",
									e.getMessage()),
							Notification.TYPE_ERROR_MESSAGE);
				}
				popup.setPopupVisible(false);
			}
		});
		layout.addComponent(startNewInstanceButton);

		if (processDefinition.hasStartFormKey()) {
			Button startNewInstanceWithVariablesButton = new Button(
					"Start a new instance and fill in the Start Form");
			startNewInstanceWithVariablesButton
					.addStyleName(Reindeer.BUTTON_SMALL);
			layout.addComponent(startNewInstanceWithVariablesButton);
		}

		return popup;
	}

	private List<ProcessDefinition> getAllProcessDefinitions() {
		ProcessDefinitionQuery query = getRepositoryService()
				.createProcessDefinitionQuery();
		return query.orderByProcessDefinitionName().asc().list();
	}

	private RepositoryService getRepositoryService() {
		return ProcessEngines.getDefaultProcessEngine().getRepositoryService();
	}

	private RuntimeService getRuntimeService() {
		return ProcessEngines.getDefaultProcessEngine().getRuntimeService();
	}

}
