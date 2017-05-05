
package cirrus.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.DocumentBackend;
import cirrus.docker.Language;
import cirrus.models.Document;
import cirrus.templates.documents.BuildMenuBar;
import cirrus.templates.documents.DocumentPanel;
import cirrus.templates.documents.DocumentTabs;
import cirrus.templates.documents.FileMenuBar;
import cirrus.templates.documents.PreferencesSubwindow;

/**
 * View for view documents.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
@SpringView(name = "document")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {
	private final DocumentBackend mBackend;
	Document doc;
	FileMenuBar fileMenuBar;
	TextField docNameField;
	BuildMenuBar buildMenuBar;
	DocumentPanel documentPanel;
	DocumentTabs documentTabs;
	PreferencesSubwindow prefWindow;

	@Autowired
	public DocumentView(DocumentBackend backend) {
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);

		fileMenuBar = new FileMenuBar();
		fileMenuBar.menuSave.setCommand(e -> save());
		fileMenuBar.menuTrash.setCommand(e -> delete());
		fileMenuBar.menuOptions.setCommand(e -> showPreferences());
		this.addComponent(fileMenuBar);

		docNameField = new TextField();
		this.addComponent(docNameField);

		buildMenuBar = new BuildMenuBar();
		buildMenuBar.setBuildCmd(e -> buildProgram());
		buildMenuBar.setRunCmd(e -> runProgram());
		this.addComponent(buildMenuBar);

		documentPanel = new DocumentPanel();
		this.addComponent(documentPanel);
		this.setExpandRatio(documentPanel, 1.0f);
		tabToIndent();

		documentTabs = new DocumentTabs();
		this.addComponent(documentTabs);

		prefWindow = new PreferencesSubwindow();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			try {
				if (!event.getParameters().equals("")) {
					int docId = Integer.parseInt(event.getParameters());
					doc = mBackend.getDocument(docId);
				} else {
					doc = new Document(mBackend.getCurrentUser(), "", "");
				}
				docNameField.setValue(doc.getDocName());
				documentPanel.setDocBody(doc.getDocBody());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void buildProgram() {
		Language lang = Language.fromExtension(getCurrFileExtension());
		if (lang != null) {
			String result = mBackend.buildProgram(doc.getDocBody(), lang);
			documentTabs.appendConsole(result);
		}
	}

	public void runProgram() {
		Language lang = Language.fromExtension(getCurrFileExtension());
		if (lang != null) {
			String result = mBackend.runProgram(doc.getDocBody(), lang);
			documentTabs.appendConsole(result);
		}
	}

	public void save() {
		doc.setDocName(docNameField.getValue());
		doc.setDocBody(documentPanel.getDocBody());
		doc.setModifyDate();
		mBackend.saveDocument(doc);
	}

	public void delete() {
		mBackend.deleteDocument(doc.getDocId());
		getUI().getNavigator().navigateTo("");
	}

	public void showPreferences() {
		try {
			UI.getCurrent().addWindow(prefWindow);
		} catch (Exception e) {
			// Window is already visible
		}
	}
	
	public void tabToIndent() {
		String script = "var textarea = document.querySelector('.v-textarea');textarea.setAttribute('spellcheck','false');"
				+ "textarea.addEventListener('keydown', function(e) {if(e.key == 'Tab') { e.preventDefault(); var s = this.selectionStart;"
				+ "if(e.shiftKey){var start = this.value.lastIndexOf('\\n',s);if (this.value.substring(s, s+1) == '\\n') {start = this.value.lastIndexOf('\\n', s - 1) }"
				+ "if (start === -1) {start = 0;}var end = this.value.indexOf('\\n', s);var slice = this.value.slice(start,end);var slice2 = slice.replace('\\t','');"
				+ "this.value = this.value.replace(slice, slice2);if (slice != slice2){this.selectionEnd = s - 1;}else{this.selectionEnd = s;}}"
				+ "else {this.value = this.value.substring(0, this.selectionStart) + '\\t' + this.value.substring(this.selectionEnd);this.selectionEnd = s + 1;}}});";
		JavaScript.getCurrent().execute(script);
	}

	private String getCurrFileExtension() {
		String docName = doc.getDocName();
		int extIndex = docName.lastIndexOf('.');
		return extIndex != -1 ? docName.substring(extIndex + 1) : "";
	}
}
