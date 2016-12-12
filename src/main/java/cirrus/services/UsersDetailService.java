package cirrus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vaadin.spring.security.VaadinSecurity;

import cirrus.models.Role;
import cirrus.models.User;
import cirrus.repositories.UserRepository;

@Service
public class UsersDetailService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	public UsersDetailService(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = repo.findByUserName(username);
		if (user == null) {
			return null;
		}
		List<GrantedAuthority> auth = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		if (user.getRole().equals(Role.ADMIN)) {
			auth = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		}
		String password = user.getPassword();
		return new org.springframework.security.core.userdetails.User(username, password,
				auth);
	}
}