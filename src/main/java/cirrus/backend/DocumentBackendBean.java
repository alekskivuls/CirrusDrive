
package cirrus.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.Document;
import cirrus.repositories.DocumentRepository;

@Service
public class DocumentBackendBean implements DocumentBackend {

	@Autowired
	DocumentRepository docRepo;

	@Override
	public String saveDocument(String docContents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getDocument(int docId) {
		return docRepo.findOne(docId);
	}
	
}
