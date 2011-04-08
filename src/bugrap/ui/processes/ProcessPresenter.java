package bugrap.ui.processes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import com.github.peholmst.mvp4vaadin.navigation.ControllablePresenter;

public class ProcessPresenter extends ControllablePresenter<ProcessView> {

	private static final long serialVersionUID = 3328656287757828734L;

	private static Logger log = Logger.getLogger(ProcessPresenter.class
			.getName());

	public ProcessPresenter(ProcessView view) {
		super(view);
	}

	@Override
	public void init() {
		getView().setProcessDefinitions(getAllProcessDefinitions());
	}

	public void startNewInstance(ProcessDefinition processDefinition) {
		log.log(Level.INFO, "Starting instance of process {1}",
				processDefinition.getKey());
		try {
			getRuntimeService().startProcessInstanceById(
					processDefinition.getId());
			getView().showProcessStartSuccess(processDefinition);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not start process instance", e);
			getView().showProcessStartFailure(processDefinition);
		}
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
