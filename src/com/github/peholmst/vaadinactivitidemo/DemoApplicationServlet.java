package com.github.peholmst.vaadinactivitidemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@WebServlet(urlPatterns = "/*", initParams = @WebInitParam(name = "widgetset", value = "com.github.peholmst.vaadinactivitidemo.DemoApplicationWidgetset"))
public class DemoApplicationServlet extends AbstractApplicationServlet {

	private static final long serialVersionUID = 3462611590222051061L;

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		return new DemoApplication();
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return DemoApplication.class;
	}

}
