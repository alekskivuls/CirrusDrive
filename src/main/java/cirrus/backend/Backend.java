package cirrus.backend;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import cirrus.models.Document;
import cirrus.models.User;

public interface Backend {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	User getCurrentUser();

	List<Document> getUsersDocs();
}
