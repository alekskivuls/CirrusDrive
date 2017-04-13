
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cirrus.Sections;
import cirrus.backend.DocumentBackend;
import cirrus.models.Document;

/**
 * View for view documents.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
@SpringView(name = "document")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {

	private final DocumentBackend mBackend;

	private final MenuBar barmenu;
	private final TextField docName;
	private final Window subWindow;
	private TextArea docBody;
	Integer docId;

	@Autowired
	public DocumentView(DocumentBackend backend)
	{
		subWindow = createWindow();
		this.mBackend = backend;
		setSizeFull();
		setMargin(true);

		// MenuBar Filew, Edit, Views, Tools?
		barmenu = new MenuBar();
		addComponent(barmenu);
		
		
		// TOP-LEVEL MENUITEM 1
		MenuItem menuFile = barmenu.addItem("File", null, null);
		// SUBMENU
		MenuItem _menuImport = menuFile.addItem("Import", null, null);
		// SUBMENU
		MenuItem menuExport = menuFile.addItem("Export", null, null);

		
		
		// TOP-LEVEL MENUITEM 2
		MenuItem menuEdit = barmenu.addItem("Edit", null, null);

		// TOP-LEVEL MENUITEM 3
		
		MenuItem menuView = barmenu.addItem("View", null, null);
		
		// TOP-LEVEL MENUITEM 4
		MenuItem servs = barmenu.addItem("Options", null, null);
		// Define a common menu command for all the menu items.
		MenuBar.Command mycommand = new MenuBar.Command() {
		    public void menuSelected(MenuItem selectedItem) {
		    	MySub sub = new MySub(selectedItem.getText());
	    	    // Add it to the root component
	    	    UI.getCurrent().addWindow(sub);
		    }
		};
		MenuItem pref = servs.addItem("Preferences", null, mycommand);
		
		// Document Name Text Field
		docName = new TextField();
		docName.setSizeUndefined();
		addComponent(docName);

		
		// Toolbar
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setSizeUndefined();

		// Save Button
		Button save = new Button();
		save.setIcon(FontAwesome.SAVE);
		save.setSizeFull();
		save.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				Document doc;
				if (docId == null)
				{
					doc = new Document(mBackend.getCurrentUser(), docName.getValue(), docBody.getValue());
					docId = doc.getDocId();
				}
				else
				{
					doc = mBackend.getDocument(docId);
					doc.setDocName(docName.getValue());
					doc.setDocBody(docBody.getValue());
					doc.setModifyDate();
				}
				mBackend.saveDocument(doc);
			}
		});
		toolbar.addComponent(save);

		Button trash = new Button();
		trash.setIcon(FontAwesome.TRASH);
		trash.setSizeFull();
		trash.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (docId != null)
					mBackend.deleteDocument(docId);
				getUI().getNavigator().navigateTo("");
			}
		});
		toolbar.addComponent(trash);

		addComponent(toolbar);

		Panel panel = createPanel();
		addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
	}
	
	private Window createWindow()
	{
		Window window = new Window("Preferences");
        window.setWidth(300.0f, Unit.PIXELS);
        final FormLayout content = new FormLayout();
        content.setMargin(true);
        window.setContent(content);
        
        return window;
	}

	private Panel createPanel()
	{
		Panel panel = new Panel();
		docBody = new TextArea();
		docBody.setWordwrap(false);
		docBody.setSizeFull();

		panel.setSizeFull();
		panel.setContent(docBody);
		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		if (event.getParameters() != null)
		{
			try {
				docId = Integer.parseInt(event.getParameters());
				Document doc = mBackend.getDocument(docId);
				docName.setValue(doc.getDocName());
				docBody.setValue(doc.getDocBody());
			} catch (Exception e) {
				return;
			}
		}
	}
	
	
	
	private class MySub extends Window {
	    public MySub(final String windowName) {
	        super(windowName); // Set window caption
	        
	        mName = windowName;
	        center();

	        this.setWidth(640, Unit.PIXELS);
	        this.setHeight(480, Unit.PIXELS);
	        // Disable the close button
	        setClosable(true);

	        setContent(new Button("Close "+mName+" Test", event -> close()));
	    }
	    
	    private final String mName;
	}
}
