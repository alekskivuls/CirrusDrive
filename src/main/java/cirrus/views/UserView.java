
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.MyBackend;

/**
 * View that is available for all users.
 */
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SpringView(name = "user")
@SideBarItem(sectionId = Sections.VIEWS, caption = "User View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class UserView extends CustomComponent implements View {

    private final MyBackend backend;

    @Autowired
    public UserView(MyBackend backend) {
        this.backend = backend;
        RichTextArea rtarea = new RichTextArea();
        setCompositionRoot(rtarea);
        
        //FIXME Hack to set size of text area, need to use layouts to fill space
        rtarea.setSizeFull();
        rtarea.setHeight("800");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
