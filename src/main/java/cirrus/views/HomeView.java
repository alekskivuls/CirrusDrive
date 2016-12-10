
package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractComponentContainer;

import cirrus.Sections;

/**
 * When the user logs in and there is no view to navigate to, this view will be shown.
 */
@Theme("HomeTheme")
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {

    public HomeView() {// @import "HomeTheme.scss";
        setSpacing(true);
        setMargin(true);

        Label header = new Label("Welcome to Cirrus Drive!");
        header.addStyleName(ValoTheme.LABEL_H1);
        addComponent(header);

        Label body = new Label("<p>This is the body. Formatted with html.</p>");
        body.setContentMode(ContentMode.HTML);
        addComponent(body);
        
        // Create a 4 by 4 grid layout.
        //CssLayout grid = new CssLayout();
        HorizontalLayout grid = new HorizontalLayout();
        MarginInfo margin = new MarginInfo(10);
        margin.setMargins(true);
        //grid.setMargin(true);
        grid.setMargin(margin);
        grid.setWidth("100%");
        grid.setSpacing(false);
        
        grid.setWidthUndefined();
        grid.addStyleName("Example Horiztonal Wrap");

        // Fill out the first row using the cursor.
        for (int i = 0; i < 20; i++) {
        	Button b = new Button("Col " + (grid.getComponentCount() + 1));
        	b.setIcon(FontAwesome.FOLDER);
        	b.setWidth("75px");
        	b.setHeight("75px");
        	b.addStyleName(BaseTheme.BUTTON_LINK);
            grid.addComponent(b);
        }

        
        grid.addComponent(new Label("1x2 cell"));
        InlineDateField date = new InlineDateField("A 2x2 date field");
        date.setResolution(DateField.RESOLUTION_DAY);
        grid.addComponent(date);
        
        addComponent(grid);
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
