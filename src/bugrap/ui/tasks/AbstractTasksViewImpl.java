package bugrap.ui.tasks;

import java.util.List;

import org.activiti.engine.task.Task;

import bugrap.ui.util.AbstractBugrapView;

import com.vaadin.Application;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.themes.Reindeer;

public abstract class AbstractTasksViewImpl<V extends TasksView, P extends TasksPresenter<V>>
		extends AbstractBugrapView<V, P> implements TasksView {

	private static final long serialVersionUID = 2607026506045321251L;

	private Table taskTable;

	private BeanItemContainer<Task> dataSource;

	private final Application application;

	public AbstractTasksViewImpl(Application application) {
		this.application = application;
		init();
	}

	@Override
	protected void initView() {
		super.initView();
		taskTable = new Table();
		dataSource = new BeanItemContainer<Task>(Task.class);
		taskTable.setContainerDataSource(dataSource);
		taskTable.setVisibleColumns(getVisibleColumns());
		taskTable.setSizeFull();
		taskTable.addGeneratedColumn("name", createNameColumnGenerator());
		getViewLayout().addComponent(taskTable);
		getViewLayout().setExpandRatio(taskTable, 1.0F);
	}

	@SuppressWarnings("serial")
	private ColumnGenerator createNameColumnGenerator() {
		return new ColumnGenerator() {

			@Override
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Task task = (Task) itemId;
				PopupView popupView = createTaskPopup(task);
				return popupView;
			}
		};
	}

	@Override
	public void setTasks(List<Task> tasks) {
		dataSource.removeAllItems();
		dataSource.addAll(tasks);
	}

	@SuppressWarnings("serial")
	@Override
	protected void addAdditionalControlsToHeader(HorizontalLayout headerLayout) {
		Button refreshButton = new Button("Refresh");
		refreshButton.addStyleName(Reindeer.BUTTON_SMALL);
		refreshButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().refreshTasks();
			}
		});
		headerLayout.addComponent(refreshButton);
		headerLayout.setComponentAlignment(refreshButton,
				Alignment.MIDDLE_RIGHT);
	}

	protected abstract PopupView createTaskPopup(final Task task);

	protected abstract String[] getVisibleColumns();

	protected final Application getApplication() {
		return application;
	}
}
