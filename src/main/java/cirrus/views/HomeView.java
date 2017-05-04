package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.backend.DocumentBackend;
import cirrus.models.Document;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */
@Theme("valo")
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {
	final Backend mBackend;
	final DocumentBackend dBackend;
	private boolean doubleClicked = false;
	private Button bTemp = null;
	private Panel sidePanel;
	private long clickTimer;
	private Window subwindow;
	
	public HomeView(Backend backend, DocumentBackend docBackend) {
		this.mBackend = backend;
		this.dBackend = docBackend;
		setSizeFull();
		setMargin(true);

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth(95, Unit.PERCENTAGE);
		
		Label header = new Label("Welcome to Cirrus Drive!");
		header.addStyleName(ValoTheme.LABEL_H1);
		titleBar.addComponent(header);
		titleBar.setExpandRatio(header, 1.0f);
		
		// Subwindow -----------------------------------------------
		subwindow = new Window("New Document");
		subwindow.setModal(true);
		subwindow.setResizable(false);

        // Window setup
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subwindow.setContent(subContent);
        
        // Text fields from user
        TextField docNameField = new TextField("Name:");
        TextField docDescripField = new TextField("Description:");

        // Button to finish subcontent and go to new document page
        Button finishDocBtn = new Button("Create");
		finishDocBtn.addClickListener(event -> {
			Document doc = new Document(docBackend.getCurrentUser(), docNameField.getValue());
			doc.setDocDescription((String)docDescripField.getValue());
			docBackend.saveDocument(doc);
			subwindow.close();
			
			getUI().getNavigator().navigateTo("document/" + doc.getDocId());
		});
        
		// Add content to subwindow
        subContent.addComponent(docNameField);
        subContent.addComponent(docDescripField);
        subContent.addComponent(finishDocBtn);
        subContent.setComponentAlignment(finishDocBtn, Alignment.BOTTOM_RIGHT);
        
        // Button to open subwindow
        Button addDocBtn = new Button(FontAwesome.PLUS);
		addDocBtn.addClickListener(event -> {
			getUI().getCurrent().addWindow(subwindow);
		});
		
		titleBar.addComponent(addDocBtn);
		titleBar.setComponentAlignment(addDocBtn, Alignment.TOP_RIGHT);
		
		addComponent(titleBar);
		
		// Main layout to hold the panel
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setSpacing(true);
        mainLayout.addLayoutClickListener(event -> {
        	// Collapse side panel 
        	if (sidePanel.isVisible()) {
        		sidePanel.setVisible(false);
        	}
        });
     
        // Panel that displays documents
        Panel panel = createMainPanel();       
        mainLayout.addComponent(panel);
        mainLayout.setExpandRatio(panel, 1.0f);
        
        // Side panel for details
        sidePanel = new Panel("Details");
        mainLayout.addComponent(sidePanel);
        sidePanel.setHeight("100%");
        sidePanel.setWidth("250px");
        sidePanel.setVisible(false);
		
        //Add layout
		addComponent(mainLayout);
		this.setExpandRatio(mainLayout, 1.0f);
	}
	
	// document list
	private Panel createMainPanel() {
		Panel panel = new Panel();
		//CssLayout layout = new CssLayout();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);  // Fix outside margin
		layout.setSpacing(true); // Fix spacing between buttons
		layout.setSizeFull();
		
		for (Document doc : mBackend.getUsersDocs()) {
			Button button = new Button(doc.getDocName());
			button.setData(doc);
			button.setIcon(FontAwesome.FOLDER);
			//button.addStyleName(BaseTheme.BUTTON_LINK);
			button.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Document doc = (Document) event.getButton().getData();
					// When successfully double clicked
					if (doubleClicked && bTemp.equals(event.getButton()) && System.currentTimeMillis()-clickTimer < 500) {
						// Open new document in document view
						getUI().getNavigator().navigateTo("document/" + doc.getDocId());
						
						// Reset double click condition and hide side panels
						doubleClicked = false;
						sidePanel.setVisible(false);
					// When single clicked
					} else {
						// Begin timer to check for double click and store current button
						clickTimer = System.currentTimeMillis();
						bTemp = event.getButton();
						doubleClicked = true;
						
						// Generate and show side-panel
						sidePanel.setVisible(true);
						sidePanel.setContent(createSidePanel(doc));
						
						button.setIcon(FontAwesome.FOLDER);
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
	
	FormLayout createSidePanel(Document doc) {
		FormLayout content = new FormLayout();
		content.addComponent(new Label("<b>Owner:</b> " + doc.getDocOwner().getUserName(), ContentMode.HTML));
		content.addComponent(new Label("<b>Name:</b> " + doc.getDocName(), ContentMode.HTML));
		content.addComponent(new Label("<b>Description:</b> " + doc.getDocDescription(), ContentMode.HTML));
		content.addComponent(new Label("<b>Date:</b> " + doc.getCreateDate(), ContentMode.HTML));
		
		if (doc.getModifyDate() != null) 
			content.addComponent(new Label("<b>Modified:</b> " + doc.getModifyDate(), ContentMode.HTML));
		
		content.setSizeFull();
		return content;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
}