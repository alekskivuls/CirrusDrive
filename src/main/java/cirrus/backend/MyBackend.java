
package cirrus.backend;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Example backend interface with
 * {@link org.springframework.security.access.prepost.PreAuthorize} annotations.
 */
public interface MyBackend {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	String adminOnlyEcho(String s);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String echo(String s);
}
