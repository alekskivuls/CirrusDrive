
package cirrus.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;

import cirrus.Sections;
import cirrus.backend.Backend;

/**
 * An operation that invokes a backend method that is available for all users.
 */
@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "User operation", order = 0)
@FontAwesomeIcon(FontAwesome.ANCHOR)
public class UserOperation implements Runnable {

    private final Backend backend;

    @Autowired
    public UserOperation(Backend backend) {
        this.backend = backend;
    }

    @Override
    public void run() {
        Notification.show(backend.echo("Hello World"));
    }
}
