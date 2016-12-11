
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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.DocumentBackend;

/**
 * View that is available for all users.
 */
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SpringView(name = "document")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {

    private final DocumentBackend mBackend;

    @Autowired
    public DocumentView(DocumentBackend backend) {
        this.mBackend = backend;
        setSizeFull();
        setMargin(true);
        
        TextField docName = new TextField();
        //docName.setSizeFull();
        docName.setSizeUndefined();
        addComponent(docName);
        
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setSizeUndefined();
        //toolbar.setSizeFull();
        
        Button save = new Button();
        save.setIcon(FontAwesome.SAVE);
        save.setSizeFull();
        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                
            }
        });
        toolbar.addComponent(save);
        
        Button trash = new Button();
        trash.setIcon(FontAwesome.TRASH);
        trash.setSizeFull();
        trash.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                
            }
        });
        toolbar.addComponent(trash);
        
        addComponent(toolbar);
        
        
        
        Panel panel = createPanel();
        addComponent(panel);
        this.setExpandRatio(panel, 1.0f);
    }
    
    Panel createPanel() {
    	Panel panel = new Panel();
        TextArea textArea = new TextArea();
        //textArea.setSizeUndefined();
        textArea.setSizeFull();
        
        
        panel.setSizeFull();
        //panel.setSizeUndefined();
        panel.setContent(textArea);
        //panel.getContent().setSizeUndefined();
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
