
package cirrus.views;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import cirrus.Sections;
import cirrus.backend.AdminBackend;
import cirrus.models.Role;
import cirrus.models.User;

/**
 * View that is available to administrators only.
 */
@Secured("ROLE_ADMIN")
@SpringView(name = "admin")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Admin View")
@FontAwesomeIcon(FontAwesome.COGS)
public class AdminView extends CustomComponent implements View {

	private final AdminBackend mBackend;

	@SuppressWarnings("deprecation")
	@Autowired
	public AdminView(AdminBackend backend) {
		this.mBackend = backend;
		Binder<User> binder = new Binder<>();

		FormLayout form = new FormLayout();
		TextField firstName = new TextField("First Name");
		firstName.setIcon(VaadinIcons.USER);
		binder.forField(firstName).bind(User::getFirstName, User::setFirstName);
		form.addComponent(firstName);

		TextField lastName = new TextField("Last Name");
		lastName.setIcon(VaadinIcons.USER);
		binder.forField(lastName).bind(User::getLastName, User::setLastName);
		form.addComponent(lastName);

		TextField userName = new TextField("Username");
		userName.setIcon(VaadinIcons.ENVELOPE);
		binder.forField(userName).asRequired("Username required").bind(User::getUserName, User::setUserName);
		form.addComponent(userName);

		PasswordField password = new PasswordField("Password");
		password.setIcon(VaadinIcons.KEY);
		binder.forField(password).asRequired("Password required").bind(User::getPassword, User::setPassword);
		form.addComponent(password);

		ComboBox<Role> roleSelect = new ComboBox<>("Role", Arrays.asList(Role.values()));
		roleSelect.setIcon(VaadinIcons.USERS);
		binder.forField(roleSelect).asRequired("Role required").bind(User::getRole, User::setRole);
		form.addComponent(roleSelect);

		Button createUser = new Button("Create user", event -> {
			try {
				User user = new User();
				binder.writeBean(user);
				mBackend.createAccount(user);
				Notification.show("User created");
			} catch (ValidationException e) {
				Notification.show("User could not be saved, " + "please check error messages for each field.");
			}
		});
		form.addComponent(createUser);

		form.setMargin(new MarginInfo(15));
		setCompositionRoot(form);

	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
	}
}
