package com.github.peholmst.vaadinactivitidemo.ui.util;

import java.util.HashMap;
import java.util.Map;

public class UserTaskFormContainer implements java.io.Serializable {

	private static final long serialVersionUID = -8090027061007059322L;

	private Map<String, Class<? extends UserTaskForm>> formMap = new HashMap<String, Class<? extends UserTaskForm>>();

	public boolean containsForm(String formKey) {
		return formMap.containsKey(formKey);
	}

	public UserTaskForm getForm(String formKey) {
		Class<? extends UserTaskForm> formClass = formMap.get(formKey);
		if (formClass == null) {
			return null;
		} else {
			try {
				return formClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void registerForm(String formKey,
			Class<? extends UserTaskForm> formClass) {
		formMap.put(formKey, formClass);
	}
}
