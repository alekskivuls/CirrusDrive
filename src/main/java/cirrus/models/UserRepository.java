package cirrus.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByLastNameStartsWithIgnoreCase(String userName);
}
