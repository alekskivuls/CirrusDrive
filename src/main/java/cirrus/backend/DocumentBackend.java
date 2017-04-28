
package cirrus.backend;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.docker.Language;
import cirrus.models.Document;
import cirrus.models.User;

public interface DocumentBackend {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	Document getDocument(int docId);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	void saveDocument(Document doc);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	void deleteDocument(int docId);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	User getCurrentUser();
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    List<User> getAllUsers();

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String buildProgram(String programSrc, Language lang);

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String runProgram(String programSrc, Language lang);
}
