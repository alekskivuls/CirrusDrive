
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import cirrus.backend.DocumentBackend;

/**
 * View that is available for all users.
 */
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SpringView(name = "document")
//@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {

    private final DocumentBackend mBackend;

    @Autowired
    public DocumentView(DocumentBackend backend) {
        this.mBackend = backend;
        TextField docName = new TextField();
        addComponent(docName);
        
        HorizontalLayout toolbar = new HorizontalLayout();
        Button save = new Button();
        save.setIcon(FontAwesome.SAVE);
        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                
            }
        });
        toolbar.addComponent(save);
        
        Button trash = new Button();
        trash.setIcon(FontAwesome.TRASH);
        trash.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                
            }
        });
        toolbar.addComponent(trash);
        
        addComponent(toolbar);
        
        TextArea textArea = new TextArea();
        //FIXME Hack to set size of text area, need to use layouts to fill space
        textArea.setSizeFull();
        textArea.setHeight("800");
        addComponent(textArea);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
