
package cirrus.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.User;
import cirrus.repositories.UserRepository;

/**
 * Implementation of {@link cirrus.backend.AdminBackend}.
 */
@Service
public class AdminBackendBean implements AdminBackend {

	@Autowired
	UserRepository userRepo;

	@Override
	public void createAccount(User user) {
		userRepo.save(user);
	}
}
