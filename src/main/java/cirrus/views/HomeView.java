
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.models.Document;

/**
 * When the user logs in and there is no view to navigate to, this view will be shown.
 */
@Theme("HomeTheme")
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {
	final Backend mBackend;
	
    public HomeView(Backend backend) {// @import "HomeTheme.scss";
		this.mBackend = backend;
    	setSizeFull();
    	//setSizeUndefined();
        setMargin(true);
        
        Label header = new Label("Welcome to Cirrus Drive!");
        header.addStyleName(ValoTheme.LABEL_H1);
        //header.setSizeFull();
        //header.setSizeUndefined();
        addComponent(header);
        
        Panel panel = createPanel();
        addComponent(panel);
        this.setExpandRatio(panel, 1.0f);
    }
    
    Panel createPanel() {
    	Panel panel = new Panel();
        CssLayout layout = new CssLayout();
        layout.setSizeFull();//layout.setSizeFull();
        
        for(Document doc : mBackend.getUsersDocs()) {
            Button button = new Button(doc.getDocName());
            button.setData(doc);
            button.setIcon(FontAwesome.FOLDER);
            button.addStyleName(BaseTheme.BUTTON_LINK);
            button.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Document doc = (Document) event.getButton().getData();
                    getUI().getNavigator().navigateTo("document/"+doc.getDocId());
                }
            });
            layout.addComponent(button);
        }
        
        /*
        // Create three equally expanding components.
        for (int i = 1; i <= 200; i++) {
        	// THIS BUTTON'S NAME SHOULD BE THE DOCUMENT'S NAME
            Button button = new Button("Doc. " + (layout.getComponentCount() + 1));
            button.setIcon(FontAwesome.FOLDER);
        	//b.setWidth("75px");
        	//b.setHeight("75px");
            button.addStyleName(BaseTheme.BUTTON_LINK);
            //button.setWidth("75px");
            //button.setHeight("75px");
            button.setWidth("10%");
            //button.setHeight("75%");
            layout.addComponent(button);
            
            // Have uniform 1:1:1 expand ratio.
            //layout.setExpandRatio(button, 1.0f);
            
        }*/
        
        panel.setSizeFull();
        panel.setContent(layout);
        panel.getContent().setSizeUndefined();
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
    /*
		GridLayout grid = new GridLayout(4, 4);
        grid.addStyleName("example-gridlayout");

        // Fill out the first row using the cursor.
        grid.addComponent(new Button("R/C 1"));
        for (int i = 0; i < 3; i++) {
            grid.addComponent(new Button("Col " + (grid.getCursorX() + 1)));
        }

        // Fill out the first column using coordinates.
        for (int i = 1; i < 4; i++) {
            grid.addComponent(new Button("Row " + i), 0, i);
        }

        // Add some components of various shapes.
        grid.addComponent(new Button("3x1 button"), 1, 1, 3, 1);
        grid.addComponent(new Label("1x2 cell"), 1, 2, 1, 3);
        InlineDateField date = new InlineDateField("A 2x2 date field");
        date.setResolution(DateField.RESOLUTION_DAY);
        grid.addComponent(date, 2, 2, 3, 3);
        
        addComponent(grid);
     */
}
