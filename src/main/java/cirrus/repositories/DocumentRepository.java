package cirrus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cirrus.models.Document;
import cirrus.models.User;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	List<Document> findByDocOwner(User user);
}
