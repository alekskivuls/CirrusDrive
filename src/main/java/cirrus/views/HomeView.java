package cirrus.views;

import java.sql.Date;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.models.Document;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */
@Theme("HomeTheme")
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {
	final Backend mBackend;
	private boolean doubleClicked = false;
	private Button bTemp = null;
	private Panel sidePanel;
	private long clickTimer;

	public HomeView(Backend backend) {
		this.mBackend = backend;
		setSizeFull();
		setMargin(true);

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth(95, Unit.PERCENTAGE);
		
		Label header = new Label("Welcome to Cirrus Drive!");
		header.addStyleName(ValoTheme.LABEL_H1);
		titleBar.addComponent(header);
		titleBar.setExpandRatio(header, 1.0f);
		
		Button addDocBtn = new Button(FontAwesome.PLUS);
		addDocBtn.setDescription("Add a new document");
		addDocBtn.setSizeUndefined();
		
		addDocBtn.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("document");
			}
		});
		
		titleBar.addComponent(addDocBtn);
		titleBar.setComponentAlignment(addDocBtn, Alignment.TOP_RIGHT);
		
		addComponent(titleBar);
		
		// Main layout to hold the panel
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setSpacing(true);
     
        // Panel that displays documents
        Panel panel = createPanel();       
        mainLayout.addComponent(panel);
        mainLayout.setExpandRatio(panel, 1.0f);
        
        // Side panel for details
        sidePanel = new Panel("Details");
        mainLayout.addComponent(sidePanel);
        sidePanel.setHeight("100%");
        sidePanel.setWidth("200px");
        sidePanel.setVisible(false);
		
        //Add layout
		addComponent(mainLayout);
		this.setExpandRatio(mainLayout, 1.0f);
		
		/*
		Panel panel = createPanel();
		addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
		*/
	}
	
	// document list
	Panel createPanel() {
		Panel panel = new Panel();
		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		
		for (Document doc : mBackend.getUsersDocs())  {
			Button button = new Button(doc.getDocName());
			button.setData(doc);
			button.setIcon(FontAwesome.FOLDER);
			button.addStyleName(BaseTheme.BUTTON_LINK);
			button.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Document doc = (Document) event.getButton().getData();
					if (doubleClicked && bTemp.equals(event.getButton()) && System.currentTimeMillis()-clickTimer < 500) {
						getUI().getNavigator().navigateTo("document/" + doc.getDocId());
						doubleClicked = false;
						sidePanel.setVisible(false);
					} else {
						clickTimer = System.currentTimeMillis();
						bTemp = event.getButton();
						doubleClicked = true;
						
						sidePanel.setVisible(true);
						sidePanel.setContent(genSidePanelContent(doc));
					}
				}
			});
			layout.addComponent(button);
		}

		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		return panel;
	}
	
	FormLayout genSidePanelContent(Document doc) {
		FormLayout content = new FormLayout();
		
		content.addStyleName("mypanelcontent");
		content.addComponent(new Label("PLACEHOLDER"));
		content.addComponent(new Label("Owner: " + doc.getDocOwner().getUserName()));
		content.addComponent(new Label("Name: " + doc.getDocName()));
		content.setSizeUndefined(); // Shrink to fit
		
		return content;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
}