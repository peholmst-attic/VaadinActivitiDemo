package bugrap.ui.processes;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.github.peholmst.mvp4vaadin.navigation.ControllableView;

public interface ProcessView extends ControllableView {

	void setProcessDefinitions(List<ProcessDefinition> definitions);

	void showProcessStartSuccess(ProcessDefinition process);

	void showProcessStartFailure(ProcessDefinition process);
}
