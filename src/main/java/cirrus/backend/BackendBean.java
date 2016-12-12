package cirrus.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.Document;
import cirrus.services.DocumentsService;
import cirrus.services.UsersService;

@Service
public class BackendBean implements Backend {
	
	@Autowired
	UsersService userService;
	
	@Autowired
	DocumentsService docsService;
	
	@Override
	public List<Document> getUsersDocs() {
		return docsService.getUserDocuments(userService.getCurrentUser());
	}

}
