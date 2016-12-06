
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
 * An operation that invokes a backend method that is available for admin users only. The operation is, however,
 * always visible in the side bar to demonstrate that the security checks on the backend layer are working.
 */
@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "Admin operation", order = 1)
@FontAwesomeIcon(FontAwesome.WRENCH)
public class AdminOperation implements Runnable {

    private final Backend backend;

    @Autowired
    public AdminOperation(Backend backend) {
        this.backend = backend;
    }

    @Override
    public void run() {
        Notification.show(backend.adminOnlyEcho("Hello Admin World"));
    }
}
