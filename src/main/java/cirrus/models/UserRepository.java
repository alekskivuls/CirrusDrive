package cirrus.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUserName(String userName);
	
	List<User> findByLastNameStartsWithIgnoreCase(String lastName);
}
