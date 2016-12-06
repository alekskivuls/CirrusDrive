
package cirrus.backend;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.models.User;

/**
 * Example backend interface with
 * {@link org.springframework.security.access.prepost.PreAuthorize} annotations.
 */
public interface Backend {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	String adminOnlyEcho(String s);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String echo(String s);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void createAccount(User user);
}
