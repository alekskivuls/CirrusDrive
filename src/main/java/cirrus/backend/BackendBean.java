
package cirrus.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cirrus.models.User;
import cirrus.models.UserRepository;

/**
 * Implementation of {@link cirrus.backend.Backend}.
 */
@Service
public class BackendBean implements Backend {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public String adminOnlyEcho(String s) {
		return "admin:" + s;
	}

	@Override
	public String echo(String s) {
		return s;
	}

	@Override
	public void createAccount(User user) {
		userRepo.save(user);
	}
}
