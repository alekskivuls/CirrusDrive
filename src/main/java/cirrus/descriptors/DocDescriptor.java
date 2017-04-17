package cirrus.descriptors;

import java.util.LinkedList;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.templates.Descriptor;

public class DocDescriptor extends Descriptor {
	/**
	 * This constructor is used to define a Layout that utilizes a cirrus drive
	 * Database Object.
	 * 
	 * @param backendType
	 * @param backend
	 */
	public DocDescriptor() {
		mLoadOrder = new LinkedList<Component>();
		this.init();
	}

	@Override
	protected void init() {
		this.mLoadOrder.add(this.initMenubar());
		this.mLoadOrder.add(this.initDocNameField());
		this.mLoadOrder.add(this.initToolbar());
		this.mLoadOrder.add(this.initBuildMenu());
		this.mLoadOrder.add(this.initPanel());
		this.mLoadOrder.add(this.initConsole());
	}

	private MenuBar initMenubar() {
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
			public void menuSelected(MenuItem selectedItem) {
				PrefSubwindow sub = new PrefSubwindow(selectedItem.getText());
				// Add it to the root component
				UI.getCurrent().addWindow(sub);
			}
		};
		MenuItem pref = menuOptions.addItem("Preferences", null, mycommand);

		return barmenu;
	}

	private HorizontalLayout initToolbar() {
		// Toolbar
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setSizeUndefined();

		toolbar.addComponent(this.initSaveButton());
		toolbar.addComponent(this.initTrashButton());
		toolbar.addComponent(this.initRunButton());

		return toolbar;
	}

	private Button initTrashButton() {
		// Save Button
		Button trash = new Button();
		trash.setId("DocumentTrash");
		trash.setIcon(FontAwesome.TRASH);
		trash.setSizeFull();
		return trash;
	}

	private Button initSaveButton() {
		// Save Button
		Button save = new Button();
		save.setId("DocumentSave");
		save.setIcon(FontAwesome.SAVE);
		save.setSizeFull();
		return save;
	}
	
	private Button initRunButton() {
		// Save Button
		Button run = new Button();
		run.setId("DocumentRun");
		run.setIcon(FontAwesome.PLAY_CIRCLE_O);
		run.setSizeFull();
		return run;
	}

	private TextField initDocNameField() {
		TextField docName = new TextField();
		docName.setId("DocumentNameField");
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

	private Panel initPanel() {
		Panel panel = new Panel();
		TextArea docBody = new TextArea();
		docBody.setWordwrap(false);
		docBody.setSizeFull();

		panel.setSizeFull();
		panel.setContent(docBody);
		return panel;
	}
	
	private Component initConsole()
	{
		TabSheet console = new TabSheet();
		console.setId("DocumentTabs");
		console.setHeight(200, Unit.PIXELS);
        console.addStyleName(ValoTheme.TABSHEET_FRAMED);
        console.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
 
        String[] tabNames = new String[] { "Console" };
        for (int i = 0; i < tabNames.length; ++i )
        {
        	// Change to TextArea and send Console Output here.
        	// Try this:
//        	TextArea docBody = new TextArea();
//    		docBody.setWordwrap(false);
//    		docBody.setSizeFull();
        	
            final Label label = new Label("Some message goes here: "+i+'\n', ContentMode.HTML);
            label.setWidth(100.0f, Unit.PERCENTAGE);
 
            final VerticalLayout tab = new VerticalLayout(label);
            tab.setMargin(true);
            tab.setSizeFull();
            
            console.addTab( tab, tabNames[i] );
        }
        
        return console;
	}

	private class PrefSubwindow extends Window
	{
		public PrefSubwindow(final String windowName)
		{
			super(windowName); // Set window caption

			mName = windowName;
			center();

			this.setWidth(400, Unit.PIXELS);
			this.setHeight(300, Unit.PIXELS);

			VerticalLayout content = new VerticalLayout();
			content.addComponent(new CheckBox("Show Line Numbers"));

			HorizontalLayout buttons = new HorizontalLayout();
			buttons.addComponent(new Button("Save"));
			buttons.addComponent(new Button("Close " + mName + " Test", event -> close()));

			// Disable the close button
			this.setClosable(false);
			this.setResizable(false);

			content.addComponent(buttons);
			this.setContent(content);
		}

		private final String mName;
	}
}
