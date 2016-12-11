package cirrus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.repositories.DocumentRepository;

@Service
public class DocumentsService {
	
	private DocumentRepository repo;
	
	@Autowired
	public DocumentsService(DocumentRepository repo) {
		this.repo = repo;
	}
	
}
