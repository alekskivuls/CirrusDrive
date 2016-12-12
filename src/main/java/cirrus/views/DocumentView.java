
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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

	TextField docName;
	TextArea docBody;
	Integer docId;

	@Autowired
	public DocumentView(DocumentBackend backend) {
		this.mBackend = backend;
		setSizeFull();
		setMargin(true);

		docName = new TextField();
		docName.setSizeUndefined();
		addComponent(docName);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setSizeUndefined();

		Button save = new Button();
		save.setIcon(FontAwesome.SAVE);
		save.setSizeFull();
		save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Document doc;
				if (docId == null) {
					doc = new Document(mBackend.getCurrentUser(), docName.getValue(), docBody.getValue());
					docId = doc.getDocId();
				} else {
					doc = mBackend.getDocument(docId);
					doc.setDocName(docName.getValue());
					doc.setDocBody(docBody.getValue());
				}
				mBackend.saveDocument(doc);
			}
		});
		toolbar.addComponent(save);

		Button trash = new Button();
		trash.setIcon(FontAwesome.TRASH);
		trash.setSizeFull();
		trash.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
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

	Panel createPanel() {
		Panel panel = new Panel();
		docBody = new TextArea();
		docBody.setSizeFull();

		panel.setSizeFull();
		panel.setContent(docBody);
		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
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
}
