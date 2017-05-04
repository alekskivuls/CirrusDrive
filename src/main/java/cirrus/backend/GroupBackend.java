
package cirrus.backend;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.models.UserGroup;
import cirrus.models.User;

public interface GroupBackend {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	UserGroup getGroup(int groupId);
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	void saveGroup(UserGroup group);
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	void deleteGroup(int groupId);
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	User getCurrentUser();
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    List<UserGroup> getAllGroups();

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String runProgram(String programSrc);
}
