package bugrap;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;

@WebListener
public class ProcessEngineServletContextListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(ProcessEngineServletContextListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Destroying process engines");
		ProcessEngines.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("Initializing process engines");
		ProcessEngines.init();
		deployProcesses();
	}
	
	private void deployProcesses() {
		log.info("Deploying processes");
		RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("bugrap/bpmn/bugrap.bpmn20.xml").deploy();
	}

}
