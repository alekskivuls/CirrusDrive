
package cirrus.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.docker.Language;
import cirrus.models.Document;
import cirrus.models.User;
import cirrus.services.DockerService;
import cirrus.services.DocumentsService;
import cirrus.services.UsersService;

@Service
public class DocumentBackendBean implements DocumentBackend {

	@Autowired
	DocumentsService documentService;

	@Autowired
	UsersService userService;

	@Autowired
	DockerService dockerService;

	@Override
	public void saveDocument(Document doc) {
		documentService.saveDocument(doc);
	}

	@Override
	public Document getDocument(int docId) {
		return documentService.getDocument(docId);
	}

	@Override
	public User getCurrentUser() {
		return userService.getCurrentUser();
	}

	@Override
	public void deleteDocument(int docId) {
		documentService.deleteDocument(docId);
	}

	@Override
	public String buildProgram(String programSrc, Language lang) {
		return dockerService.buildProgram(programSrc, lang);
	}

	@Override
	public String runProgram(String programSrc, Language lang) {
		return dockerService.runProgram(programSrc, lang);
	}

}
