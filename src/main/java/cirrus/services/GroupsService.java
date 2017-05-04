package cirrus.services;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import cirrus.models.Document;
import cirrus.models.UserGroup;
import cirrus.repositories.DocumentRepository;
import cirrus.repositories.GroupRepository;
 
@Service
public class GroupsService {
    private GroupRepository repo;
   
    @Autowired
    public GroupsService(GroupRepository repo) {
        this.repo = repo;
    }
   
    public UserGroup getGroup(int groupID) {
        return repo.findOne(groupID);
    }
   
    public List<UserGroup> getAllGroups() {
        return repo.findAll();
    }
   
    public void saveGroup(UserGroup group) {
        repo.save(group);
    }
   
    public void deleteGroup(int groupID) {
        repo.delete(repo.findOne(groupID));
    }
 
}