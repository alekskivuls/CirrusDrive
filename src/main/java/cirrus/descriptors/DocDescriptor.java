package cirrus.descriptors;

import java.util.LinkedList;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
		
		// Display line numbers on left side using JS
		// TODO: Figure out how to move style code to CSS file
		StringBuilder sb = new StringBuilder("var panel = document.querySelector('div.v-panel-content');panel.style.setProperty('float','right');");
		sb.append("var textPanel = document.querySelector('.v-panel-content'); var textArea = document.querySelector('textarea');")
			.append("var p = document.createElement('div');p.classList.add('lineNumbers');p.style.setProperty('position','absolute');")
			.append("p.style.setProperty('width','30px');p.style.setProperty('height','100%');textArea.style.setProperty('padding-left','30px');")
			.append("textArea.style.setProperty('font-family','courier'); p.style.setProperty('font-family','courier');textPanel.insertBefore(p, textArea);")
			.append("for (var i = 1; i <= 50; i++) {var num = document.createElement('p');num.innerHTML = i;p.appendChild(num);num.style.setProperty('margin','0');}")
			.append("p.style.setProperty('margin','0'); p.style.setProperty('border','solid .5px #ddd');");
								
		String script = sb.toString();	
		//String script = "function repeat(){var iframedDocument = document.querySelector('.gwt-RichTextArea').contentWindow.document;var p = iframedDocument.createElement('div');p.style.setProperty('float','left');p.style.setProperty('background-color','red');p.style.setProperty('width','30px');p.style.setProperty('height','100%');iframedDocument.body.appendChild(p);for (var i = 1; i <= 20; i++) {var num = iframedDocument.createElement('p');num.innerHTML = i;p.appendChild(num);}}setTimeout(repeat,3000);";

		JavaScript.getCurrent().execute(script);
		

	}

	@Override
	protected void init()
	{
		this.mLoadOrder.add( this.initMenubar() );
		this.mLoadOrder.add( this.initDocNameField() );
		this.mLoadOrder.add( this.initToolbar() );
		this.mLoadOrder.add( this.initBuildMenu());
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
	
	private MenuBar initBuildMenu() {
		MenuBar buildMenu = new MenuBar();
		MenuItem build = buildMenu.addItem("Build", FontAwesome.CHECK_CIRCLE, null);		
		MenuItem run = buildMenu.addItem("Run", FontAwesome.PLAY_CIRCLE_O, null);
		MenuItem stop = buildMenu.addItem("Stop", FontAwesome.STOP_CIRCLE_O, null);		
		return buildMenu;
	}
	
	private Panel initPanel()
	{
		Panel panel = new Panel();
		TextArea docBody = new TextArea();
		//docBody.setWordwrap(false);
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

	        this.setWidth(400, Unit.PIXELS);
	        this.setHeight(300, Unit.PIXELS);
	        
	        VerticalLayout content = new VerticalLayout();
	        content.addComponent(new CheckBox("Show Line Numbers"));
	        
	        HorizontalLayout buttons = new HorizontalLayout();
	        buttons.addComponent(new Button("Save"));
	        buttons.addComponent(new Button("Close "+mName+" Test", event -> close()));
	        
	        // Disable the close button
	        this.setClosable(false);
	        this.setResizable(false);

	        content.addComponent(buttons);
	        this.setContent(content);
	    }
	    
	    private final String mName;
	}
}
