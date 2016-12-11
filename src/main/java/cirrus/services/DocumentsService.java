package cirrus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.Document;
import cirrus.models.User;
import cirrus.repositories.DocumentRepository;

@Service
public class DocumentsService {
	
	private DocumentRepository repo;
	
	@Autowired
	public DocumentsService(DocumentRepository repo) {
		this.repo = repo;
	}
	
	public Document getDocument(int docId) {
		return repo.getOne(docId);
	}
	
	public void setDocumentBody(int docId, String body) {
		Document doc = getDocument(docId);
		doc.setDocBody(body);
		repo.save(doc);
	}
	
	public List<Document> getUserDocuments(User user) {
		return repo.findByDocOwner(user);
	}
}