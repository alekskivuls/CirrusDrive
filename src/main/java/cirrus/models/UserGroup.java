package cirrus.models;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class UserGroup {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private int groupId;
	private String groupLabel;
	private HashSet<User> groupMembers;
	
	@ManyToOne
	private User groupOwner;
	

	protected UserGroup() {
	}
	
	public UserGroup(String groupLabel, User groupOwner) {
		this.groupLabel = groupLabel;
		this.groupOwner = groupOwner;
		this.groupMembers = new HashSet<User>();
	}
	
	public UserGroup(String groupLabel, User groupOwner, HashSet<User> groupMembers){
		this.groupLabel = groupLabel;
		this.groupOwner = groupOwner;
		this.groupMembers = groupMembers;

	}
	
	public int getGroupId() {
		return groupId;
	}

	public String getGroupLabel() {
		return groupLabel;
	}
	
	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}
	
	public void addGroupMember(User user) {
		groupMembers.add(user);
	}
	
	public void removeGroupMember(User user) {
		groupMembers.remove(user);
	}
	
	public HashSet<User> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(HashSet<User> groupMembers) {
		this.groupMembers = groupMembers;
	}

	@Override
	public String toString() {
		HashSet<String> members = new HashSet<String>();
		for (User member: groupMembers) {
			members.add(member.getUserName());
		}
		return "Group [groupLabel=" + groupLabel + ", groupId=" + groupId + ", groupOwner=" + groupOwner.getUserName() + 
				", groupMembers=" + members + "]";
	}
	
	
	
	
	

}
