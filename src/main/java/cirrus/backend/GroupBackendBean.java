package cirrus.backend;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.User;
import cirrus.models.UserGroup;
import cirrus.services.GroupsService;
 
@Service
public class GroupBackendBean implements GroupBackend {
   
    @Autowired
    GroupsService groupsService;
   
    @Override
    public UserGroup getGroup(int groupId) {
        return groupsService.getGroup(groupId);
    }
 
    @Override
    public void deleteGroup(int groupId) {
        groupsService.deleteGroup(groupId);
       
    }
 
    @Override
    public void saveGroup(UserGroup group) {
        groupsService.saveGroup(group);
    }
 
    @Override
    public List<UserGroup> getAllGroups() {
        return groupsService.getAllGroups();
       
    }

	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String runProgram(String programSrc) {
		// TODO Auto-generated method stub
		return null;
	}
 
}