
package cirrus.backend;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.models.User;

public interface AdminBackend {
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void createAccount(User user);
}
