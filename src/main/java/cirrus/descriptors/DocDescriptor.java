package cirrus.descriptors;

import java.util.LinkedList;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.templates.Descriptor;
import cirrus.templates.documents.BuildMenuBar;
import cirrus.templates.documents.DocNameField;
import cirrus.templates.documents.DocumentPanel;
import cirrus.templates.documents.DocumentTabs;
import cirrus.templates.documents.FileMenuBar;

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
		this.mLoadOrder.add(new FileMenuBar());
		this.mLoadOrder.add(new DocNameField());
		// this.mLoadOrder.add(this.initToolbar());
		this.mLoadOrder.add(new BuildMenuBar());
		this.mLoadOrder.add(new DocumentPanel());
		this.mLoadOrder.add(new DocumentTabs());
	}

	// private HorizontalLayout initToolbar() {
	// // Toolbar
	// HorizontalLayout toolbar = new HorizontalLayout();
	// toolbar.setSizeUndefined();
	//
	// toolbar.addComponent(this.initSaveButton());
	// toolbar.addComponent(this.initTrashButton());
	// toolbar.addComponent(this.initRunButton());
	//
	// return toolbar;
	// }
	//
	// private Button initTrashButton() {
	// // Save Button
	// Button trash = new Button();
	// trash.setId("DocumentTrash");
	// trash.setIcon(FontAwesome.TRASH);
	// trash.setSizeFull();
	// return trash;
	// }
	//
	// private Button initSaveButton() {
	// // Save Button
	// Button save = new Button();
	// save.setId("DocumentSave");
	// save.setIcon(FontAwesome.SAVE);
	// save.setSizeFull();
	// return save;
	// }
	//
	// private Button initRunButton() {
	// // Save Button
	// Button run = new Button();
	// run.setId("DocumentRun");
	// run.setIcon(FontAwesome.PLAY_CIRCLE_O);
	// run.setSizeFull();
	// return run;
	// }

	private TextField initDocNameField() {
		TextField docName = new TextField();
		docName.setId("DocumentNameField");
		docName.setSizeUndefined();
		return docName;
	}

	// private MenuBar initBuildMenu() {
	// MenuBar buildMenu = new MenuBar();
	// MenuItem build = buildMenu.addItem("Build", FontAwesome.CHECK_CIRCLE,
	// null);
	// MenuItem run = buildMenu.addItem("Run", FontAwesome.PLAY_CIRCLE_O, null);
	// MenuItem stop = buildMenu.addItem("Stop", FontAwesome.STOP_CIRCLE_O,
	// null);
	// return buildMenu;
	// }

	private Panel initPanel() {
		Panel panel = new Panel();
		TextArea docBody = new TextArea();
		docBody.setWordwrap(false);
		docBody.setSizeFull();

		panel.setSizeFull();
		panel.setContent(docBody);
		return panel;
	}

	private Component initConsole() {
		TabSheet console = new TabSheet();
		console.setId("DocumentTabs");
		console.setHeight(200, Unit.PIXELS);
		console.addStyleName(ValoTheme.TABSHEET_FRAMED);
		console.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		String[] tabNames = new String[] { "Console" };
		for (int i = 0; i < tabNames.length; ++i) {
			// Change to TextArea and send Console Output here.
			// Try this:
			// TextArea docBody = new TextArea();
			// docBody.setWordwrap(false);
			// docBody.setSizeFull();

			final Label label = new Label("Some message goes here: " + i + '\n', ContentMode.HTML);
			label.setWidth(100.0f, Unit.PERCENTAGE);

			final VerticalLayout tab = new VerticalLayout(label);
			tab.setMargin(true);
			tab.setSizeFull();

			console.addTab(tab, tabNames[i]);
		}

		return console;
	}
}
