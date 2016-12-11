package cirrus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cirrus.models.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
