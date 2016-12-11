package cirrus.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cirrus.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String userName);
	
	List<User> findByLastNameStartsWithIgnoreCase(String lastName);
}
