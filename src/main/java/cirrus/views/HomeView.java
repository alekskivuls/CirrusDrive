package cirrus.views;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractField;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
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
		
		/*
		// Old Button -------------------------------------------------
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
		//-------------------------------------------------------------
		*/
		
		// Main layout to hold the panel -----------------------------
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
        sidePanel.setWidth("250px");
        sidePanel.setVisible(false);
		
        //Add layout
		addComponent(mainLayout);
		this.setExpandRatio(mainLayout, 1.0f);
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
		content.addComponent(new Label("<b>Owner:</b> " + doc.getDocOwner().getUserName(), com.vaadin.shared.ui.label.ContentMode.HTML));
		content.addComponent(new Label("<b>Name:</b> " + doc.getDocName(), com.vaadin.shared.ui.label.ContentMode.HTML));
		content.addComponent(new Label("<b>Description:</b> " + doc.getDocDescription(), com.vaadin.shared.ui.label.ContentMode.HTML));
		content.addComponent(new Label("<b>Date:</b> " + doc.getDate(), com.vaadin.shared.ui.label.ContentMode.HTML));
		content.setSizeFull();
		
		return content;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
}