package cirrus.Descriptors;

import java.util.LinkedList;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import cirrus.templates.Descriptor;

public class DocDescriptor extends Descriptor
{
	/**
	 * This constructor is used to define a Layout that utilizes a cirrus drive Database Object.
	 * 
	 * @param backendType
	 * @param backend
	 */
	public DocDescriptor()
	{
		mLoadOrder = new LinkedList<Component>();
		this.init();
	}

	@Override
	protected void init()
	{
		this.mLoadOrder.add( this.initMenubar() );
		this.mLoadOrder.add( this.initDocNameField() );
		this.mLoadOrder.add( this.initToolbar() );
		this.mLoadOrder.add( this.initPanel() );
	}
	
	private MenuBar initMenubar()
	{
		// MenuBar File, Edit, Views, Tools?
		MenuBar barmenu = new MenuBar();
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
		MenuItem menuOptions = barmenu.addItem("Options", null, null);
		// Define a common menu command for all the menu items.
		MenuBar.Command mycommand = new MenuBar.Command() {
		    public void menuSelected( MenuItem selectedItem ) {
		    	PrefSubwindow sub = new PrefSubwindow( selectedItem.getText() );
	    	    // Add it to the root component
	    	    UI.getCurrent().addWindow( sub );
		    }
		};
		MenuItem pref = menuOptions.addItem( "Preferences", null, mycommand );
		
		return barmenu;
	}
	
	
	private HorizontalLayout initToolbar()
	{
		// Toolbar
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setSizeUndefined();

		
		toolbar.addComponent( this.initSaveButton() );
		toolbar.addComponent( this.initTrashButton() );
		
		return toolbar;
	}
	
	private Button initTrashButton()
	{
		// Save Button
		Button trash = new Button();
		trash.setId( "DocumentTrash" );
		trash.setIcon(FontAwesome.TRASH);
		trash.setSizeFull();
		return trash;
	}
	
	private Button initSaveButton()
	{
		// Save Button
		Button save = new Button();
		save.setId( "DocumentSave" );
		save.setIcon(FontAwesome.SAVE);
		save.setSizeFull();
		return save;
	}
	
	private TextField initDocNameField()
	{
		TextField docName = new TextField();
		docName.setId( "DocumentNameField" );
		docName.setSizeUndefined();
		return docName;
	}
	
	private Panel initPanel()
	{
		Panel panel = new Panel();
		TextArea docBody = new TextArea();
		docBody.setWordwrap(false);
		docBody.setSizeFull();

		panel.setSizeFull();
		panel.setContent(docBody);
		return panel;
	}
	
	private class PrefSubwindow extends Window {
	    public PrefSubwindow(final String windowName) {
	        super(windowName); // Set window caption
	        
	        mName = windowName;
	        center();

	        this.setWidth(640, Unit.PIXELS);
	        this.setHeight(480, Unit.PIXELS);
	        // Disable the close button
	        this.setClosable(true);

	        this.setContent(new Button("Close "+mName+" Test", event -> close()));
	    }
	    
	    private final String mName;
	}
}
