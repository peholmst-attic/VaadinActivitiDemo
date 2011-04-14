package com.github.peholmst.vaadinactivitidemo.ui.main;

import com.github.peholmst.mvp4vaadin.View;

public interface MainView extends View {

	void setNumberOfUnassignedTasks(long taskCount);

	void setNumberOfMyTasks(long taskCount);

	void setNameOfCurrentUser(String username);

}
