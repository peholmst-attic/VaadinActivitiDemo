package bugrap.identity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class GroupMembershipWindow extends Window implements
		Button.ClickListener {

	private static final long serialVersionUID = 478903962853375135L;

	private final String userId;

	public GroupMembershipWindow(String userId) {
		super("Manage Group Memberships");
		setModal(true);
		this.userId = userId;
		initComponents();
	}

	private void initComponents() {
		Label title = new Label(String.format("Groups for user %s:", userId));
		addComponent(title);

		List<Group> allGroups = getAllGroups();
		Set<String> userGroupIds = getUserGroupIds();
		for (Group group : allGroups) {
			CheckBox checkbox = new CheckBox(String.format("%s (%s)",
					group.getId(), group.getName()));
			checkbox.setImmediate(true);
			checkbox.setValue(userGroupIds.contains(group.getId()));
			checkbox.setData(group);
			checkbox.addListener(this);
			addComponent(checkbox);
		}
		setWidth("300px");
	}

	private List<Group> getAllGroups() {
		GroupQuery query = getIdentityService().createGroupQuery();
		return query.orderByGroupId().asc().list();
	}

	private Set<String> getUserGroupIds() {
		GroupQuery query = getIdentityService().createGroupQuery();
		List<Group> groupList = query.groupMember(userId).list();
		HashSet<String> groupIdSet = new HashSet<String>();
		for (Group group : groupList) {
			groupIdSet.add(group.getId());
		}
		return groupIdSet;
	}

	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Group group = (Group) event.getButton().getData();
		boolean checked = event.getButton().booleanValue();
		if (checked) {
			getIdentityService().createMembership(userId, group.getId());
		} else {
			getIdentityService().deleteMembership(userId, group.getId());
		}
	}

}
