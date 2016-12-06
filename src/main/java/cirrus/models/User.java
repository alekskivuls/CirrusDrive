package cirrus.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Role role;

	protected User() {
	}
	
	public User(String userName, String password, String firstName, String lastName, Role role) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public User(String userName, String password, String firstName, String lastName, String role) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = Role.fromString(role);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return String.format("User[userName='%s', passwod='%s', firstName='%s', lastName='%s', role='%s']", userName, password, firstName, lastName, role);
	}

}
