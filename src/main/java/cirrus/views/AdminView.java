
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;

import cirrus.Sections;
import cirrus.backend.MyBackend;

/**
 * View that is available to administrators only.
 */
@Secured("ROLE_ADMIN")
@SpringView(name = "admin")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Admin View")
@FontAwesomeIcon(FontAwesome.COGS)
public class AdminView extends CustomComponent implements View {

    private final MyBackend backend;

    @Autowired
    public AdminView(MyBackend backend) {
        this.backend = backend;
        Button button = new Button("Call admin backend", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show(AdminView.this.backend.adminOnlyEcho("Hello Admin World!"));
            }
        });
        setCompositionRoot(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
