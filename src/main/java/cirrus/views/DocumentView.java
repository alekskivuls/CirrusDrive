
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
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.DocumentBackend;
import cirrus.descriptors.DocDescriptor;
import cirrus.models.Document;
import cirrus.templates.Descriptor;
import cirrus.templates.documents.BuildMenuBar;
import cirrus.templates.documents.DocNameField;
import cirrus.templates.documents.DocumentPanel;
import cirrus.templates.documents.DocumentTabs;
import cirrus.templates.documents.FileMenuBar;

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
	DocNameField docNameField;
	BuildMenuBar buildMenuBar;
	DocumentPanel documentPanel;
	DocumentTabs documentTabs;

	@Autowired
	public DocumentView(DocumentBackend backend) {
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);

		fileMenuBar = new FileMenuBar();
		fileMenuBar.setSaveCmd(e -> save());
		fileMenuBar.setTrashCmd(e -> delete());
		this.addComponent(fileMenuBar);

		docNameField = new DocNameField();
		this.addComponent(docNameField);

		buildMenuBar = new BuildMenuBar();
		buildMenuBar.setRunCmd(e -> runProgram());
		this.addComponent(buildMenuBar);

		documentPanel = new DocumentPanel();
		this.addComponent(documentPanel);
		this.setExpandRatio(documentPanel, 1.0f);

		documentTabs = new DocumentTabs();
		this.addComponent(documentTabs);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			try {
				int docId = Integer.parseInt(event.getParameters());
				doc = mBackend.getDocument(docId);
				docNameField.setDocName(doc.getDocName());
				documentPanel.setDocBody(doc.getDocBody());
				System.out.println(doc.getDocName());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("null");
			doc = new Document(mBackend.getCurrentUser(), "", "");
		}
	}

	public void runProgram() {
		String result = mBackend.runProgram(doc.getDocBody());
		documentTabs.appendConsole(result);
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
}
