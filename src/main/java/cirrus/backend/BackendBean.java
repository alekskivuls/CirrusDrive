package cirrus.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.spring.security.VaadinSecurity;

import cirrus.models.Document;
import cirrus.models.User;
import cirrus.services.DocumentsService;
import cirrus.services.UsersService;

@Service
public class BackendBean implements Backend {

	@Autowired
	VaadinSecurity vaadinSecurity;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	DocumentsService docsService;
	
	@Override
	public User getCurrentUser() {
		String userName = vaadinSecurity.getAuthentication().getName();
		return userService.getUser(userName);
	}

	@Override
	public List<Document> getUsersDocs() {
		return docsService.getUserDocuments(getCurrentUser());
	}
	
	

}
