
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

import cirrus.Sections;
import cirrus.backend.AdminBackend;
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

		PropertysetItem item = new PropertysetItem();
		item.addItemProperty("fName", new ObjectProperty<String>(""));
		item.addItemProperty("lName", new ObjectProperty<String>(""));
		item.addItemProperty("username", new ObjectProperty<String>(""));
		item.addItemProperty("password", new ObjectProperty<String>(""));
		item.addItemProperty("role", new ObjectProperty<String>(""));
		FieldGroup binder = new FieldGroup(item);

		FormLayout form = new FormLayout();
		TextField tf1 = new TextField("First Name");
		binder.bind(tf1, "fName");
		tf1.setIcon(FontAwesome.USER);
		tf1.setRequired(true);
		tf1.addValidator(new NullValidator("Must be given", false));
		form.addComponent(tf1);

		TextField tf2 = new TextField("Last Name");
		binder.bind(tf2, "lName");
		tf2.setIcon(FontAwesome.USER);
		tf2.setRequired(true);
		tf2.addValidator(new NullValidator("Must be given", false));
		form.addComponent(tf2);

		TextField tf3 = new TextField("Username");
		binder.bind(tf3, "username");
		tf3.setIcon(FontAwesome.ENVELOPE);
		tf3.setRequired(true);
		tf3.addValidator(new NullValidator("Must be given", false));
		form.addComponent(tf3);

		PasswordField tf4 = new PasswordField("Password");
		binder.bind(tf4, "password");
		tf4.setIcon(FontAwesome.KEY);
		tf4.setRequired(true);
		tf4.addValidator(new NullValidator("Must be given", false));
		form.addComponent(tf4);

		Select select = new Select("Role");
		binder.bind(select, "role");
		select.addItem("User");
		select.addItem("Admin");
		select.setRequired(true);
		form.addComponent(select);

		Button button = new Button("Create user", new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				User user = new User(binder.getField("username").toString(), binder.getField("password").toString(),
						binder.getField("fName").toString(), binder.getField("lName").toString(),
						binder.getField("role").toString());
				AdminView.this.mBackend.createAccount(user);
				Notification.show("User created");
			}
		});
		form.addComponent(button);

		form.setMargin(new MarginInfo(15));
		setCompositionRoot(form);

	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
	}
}
