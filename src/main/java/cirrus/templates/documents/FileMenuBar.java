package cirrus.templates.documents;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;

public class FileMenuBar extends MenuBar {

	MenuItem menuFile, menuImport, menuExport, menuSave, menuTrash, menuEdit, menuView, menuOptions;

	public FileMenuBar() {
		// MenuBar File, Edit, Views, Tools?
		// TOP-LEVEL MENUITEM 1
		menuFile = addItem("File", null, null);
		// SUBMENU
		menuImport = menuFile.addItem("Import", null, null);
		// SUBMENU
		menuExport = menuFile.addItem("Export", null, null);
		// SUBMENU
		menuSave = menuFile.addItem("Save", FontAwesome.SAVE, null);
		// SUBMENU
		menuTrash = menuFile.addItem("Trash", FontAwesome.TRASH, null);
		// TOP-LEVEL MENUITEM 2
		menuEdit = addItem("Edit", null, null);
		// TOP-LEVEL MENUITEM 3
		menuView = addItem("View", null, null);
		// TOP-LEVEL MENUITEM 4
		menuOptions = addItem("Options", null, null);
		// Define a common menu command for all the menu items.
		// MenuBar.Command mycommand = new MenuBar.Command() {
		// public void menuSelected(MenuItem selectedItem) {
		// PrefSubwindow sub = new PrefSubwindow(selectedItem.getText());
		// // Add it to the root component
		// UI.getCurrent().addWindow(sub);
		// }
		// };
		// MenuItem pref = menuOptions.addItem("Preferences", null, mycommand);
	}
	
	public void setSaveCmd(Command command) {
		menuSave.setCommand(command);
	}
	
	public void setTrashCmd(Command command) {
		menuTrash.setCommand(command);
	}
}
