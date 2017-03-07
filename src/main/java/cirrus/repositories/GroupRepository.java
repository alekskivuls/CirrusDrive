package cirrus.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cirrus.models.UserGroup;
import cirrus.models.User;

public interface GroupRepository extends JpaRepository<UserGroup, Integer> {
	UserGroup findByGroupLabel(String groupLabel);
}
