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

import cirrus.models.Document;
import cirrus.models.Role;
import cirrus.models.User;
import cirrus.repositories.DocumentRepository;
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
	public CommandLineRunner loadData(UserRepository repository, DocumentRepository docRepo) {
		return (args) -> {
			// save a couple of customers
			repository.save(new User("adoe", "ad", "Alice", "Doe", Role.ADMIN));
			repository.save(new User("bdoe", "bd", "Bob", "Doe", Role.USER));
			User regular = new User("user", "password", "first", "last", Role.USER);
			repository.save(regular);
			User admin = new User("admin", "password", "first", "last", Role.ADMIN);
			repository.save(admin);

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
			
			Document doc1 = new Document("Doc1", admin);
			docRepo.save(doc1);
			Document doc2 = new Document("Doc2", regular);
			docRepo.save(doc2);
			Document doc3 = new Document("Doc3", admin);
			docRepo.save(doc3);
			Document doc4 = new Document("Doc4", admin);
			docRepo.save(doc4);
			
			System.out.println("Documents found with findByDocOwner(admin): ");
			System.out.println("--------------------------------");
			for (Document doc : docRepo.findByDocOwner(admin)) {
				System.out.println(doc.toString());
			}
			System.out.println();
			
		};
	}
    
}
