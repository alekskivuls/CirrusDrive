package cirrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

import cirrus.models.Role;
import cirrus.models.User;
import cirrus.repositories.UserRepository;
import cirrus.services.UsersService;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@EnableVaadinManagedSecurity
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	 /**
     * Provide custom system messages to make sure the application is reloaded when the session expires.
     */
    @Bean
    SystemMessagesProvider systemMessagesProvider() {
        return new SystemMessagesProvider() {
            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
                CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
                systemMessages.setSessionExpiredNotificationEnabled(false);
                return systemMessages;
            }
        };
    }

    /**
     * Configure the authentication manager.
     */
    @Configuration
    static class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

    	@Autowired
		private UsersService users;
    	
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(users);
		}
	}

	@Bean
	public CommandLineRunner loadData(UserRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new User("adoe", "ad", "Alice", "Doe", Role.ADMIN));
			repository.save(new User("bdoe", "bd", "Bob", "Doe", Role.USER));
			repository.save(new User("user", "password", "first", "last", Role.USER));
			repository.save(new User("admin", "password", "first", "last", Role.ADMIN));

			// fetch all users
			for (User user : repository.findAll()) {
				System.out.println(user.toString());
			}

			// fetch an individual customer by ID
			User user = repository.findByUserName("user");
			System.out.println("User found with findOne(user):");
			System.out.println("--------------------------------");
			System.out.println(user.toString());
			System.out.println();
			
			// fetch customers by last name
			System.out.println("User found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			System.out.println("--------------------------------------------");
			for (User u : repository
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				System.out.println(u.toString());
			}
			System.out.println();
		};
	}
    
}
