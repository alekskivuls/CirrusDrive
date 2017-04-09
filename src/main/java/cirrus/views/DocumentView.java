
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
<<<<<<< HEAD
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
=======
import com.vaadin.ui.Component;
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.Descriptors.DocDescriptor;
import cirrus.backend.DocumentBackend;
import cirrus.models.Document;
import cirrus.templates.Descriptor;

/**
 * View for view documents.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
@SpringView(name = "document")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {
	private final 	Descriptor	mDocView;
	
	private final DocumentBackend mBackend;
<<<<<<< HEAD

	private final MenuBar barmenu;
	private final MenuBar buildMenu;
	private final TextField docName;
	private final Window subWindow;
=======
	private TextField docName;
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
	private TextArea docBody;
	Integer docId;

	@Autowired
	public DocumentView(DocumentBackend backend)
	{
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);
		
		mDocView = new DocDescriptor();
		setSizeFull();
		setMargin(true);
		
		
<<<<<<< HEAD
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
=======
		for( Component component : mDocView.getLoadOrder() )
		{
			if( component instanceof TextField )
			{
				TextField text = (TextField) component;
				if( text.getId().equals( "DocumentNameField" ) )
				{
					docName = text;
				}
			}
			else if( component instanceof HorizontalLayout )
			{
				HorizontalLayout layout = (HorizontalLayout) component;
				this.setButtonListeners(layout);
			}
			
			this.addComponent( component );
			
			if( component instanceof Panel )
			{
				Panel panel = (Panel) component;
				this.setExpandRatio(panel, 1.0f);
				docBody = (TextArea) panel.getContent();
				panel.getComponentCount();
			}
		}
	}
	
	private void setButtonListeners( HorizontalLayout layout )
	{
		for( int i = 0; i < layout.getComponentCount(); ++i )
		{
			Component component = layout.getComponent( i );
			if( component instanceof Button )
			{
				Button button = (Button) component;
				if( button.getId().equals("DocumentSave") )
				{
					button.addClickListener( this.createSaveAction() );
				}
				else if( button.getId().equals("DocumentTrash") )
				{
					button.addClickListener( this.createTrashAction() );
				}
			}
		}
	}
	
	private Button.ClickListener createTrashAction()
	{
		Button.ClickListener listener = new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (docId != null)
					mBackend.deleteDocument(docId);
				getUI().getNavigator().navigateTo("");
			}
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
		};
		
<<<<<<< HEAD
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
=======
		return listener;
	}
	
	private Button.ClickListener createSaveAction()
	{
		Button.ClickListener listener = new Button.ClickListener()
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
		{
			public void buttonClick(ClickEvent event)
			{
				Document doc = mBackend.getDocument(docId);
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
				}
				mBackend.saveDocument(doc);
			}
<<<<<<< HEAD
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
		
		// IDE Menubar
		buildMenu = new MenuBar();
		addComponent(buildMenu);
		
		MenuItem build = buildMenu.addItem("Build", FontAwesome.CHECK_CIRCLE, null);		
		MenuItem run = buildMenu.addItem("Run", FontAwesome.PLAY_CIRCLE_O, null);
		MenuItem stop = buildMenu.addItem("Stop", FontAwesome.STOP_CIRCLE_O, null);		

		
		// Document body
		Panel panel = createPanel();
		addComponent(panel);
		this.setExpandRatio(panel, 1.0f);

	}
	
	private Window createWindow()
	{
		Window window = new Window("Preferences");
        window.setWidth(300.0f, Unit.PIXELS);
        FormLayout content = new FormLayout();
		content.addComponent(new CheckBox("Show line numbers"));
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

=======
		};
		
		return listener;
	}
	
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
	@Override
	public void enter(ViewChangeEvent event)
	{
		if (event.getParameters() != null)
		{
			try {
				docId = Integer.parseInt( event.getParameters() );
				Document doc = mBackend.getDocument(docId);
				docName.setValue(doc.getDocName());
				docBody.setValue(doc.getDocBody());
			} catch (Exception e) {
				return;
			}
		}
	}
<<<<<<< HEAD
	
	
	
	private class MySub extends Window {
	    public MySub(final String windowName) {
	        super(windowName); // Set window caption
	        
	        mName = windowName;
	        center();

	        this.setWidth(400, Unit.PIXELS);
	        this.setHeight(300, Unit.PIXELS);
	        
	        VerticalLayout content = new VerticalLayout();
	        content.addComponent(new CheckBox("Show Line Numbers"));
	        
	        HorizontalLayout buttons = new HorizontalLayout();
	        buttons.addComponent(new Button("Save"));
	        buttons.addComponent(new Button("Close "+mName+" Test", event -> close()));
	        
	        // Disable the close button
	        setClosable(false);
	        setResizable(false);
	        content.addComponent(buttons);
	        setContent(content);
	    }
	    
	    private final String mName;
	}
}
=======
}
>>>>>>> a22352aee29827d3ecc813109c3ccc16b7856d81
