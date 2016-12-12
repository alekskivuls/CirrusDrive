
package cirrus.backend;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.models.Document;

public interface DocumentBackend {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	String saveDocument(String docContents);
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	Document getDocument(int docId);
}
