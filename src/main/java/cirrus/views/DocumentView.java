
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

/**
 * View for view documents.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
@SpringView(name = "document")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Document View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class DocumentView extends VerticalLayout implements View {
	private final Descriptor mDocView;

	private final DocumentBackend mBackend;
	private TextField docName;
	private TextArea docBody;
	Integer docId;

	@Autowired
	public DocumentView(DocumentBackend backend) {
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);

		mDocView = new DocDescriptor();
		this.setSizeFull();
		this.setMargin(true);

		for ( Component component : mDocView.getLoadOrder() )
		{
			if ( component instanceof TextField )
			{
				TextField text = (TextField) component;
				if ( text.getId().equals("DocumentNameField") )
					docName = text;
			}
			else if ( component instanceof HorizontalLayout )
			{
				HorizontalLayout layout = (HorizontalLayout) component;
				this.setButtonListeners(layout);
			}
			
			this.addComponent(component);
			
			if ( component instanceof Panel )
			{
				Panel panel = (Panel) component;
				this.setExpandRatio(panel, 1.0f);
				docBody = (TextArea) panel.getContent();
				panel.getComponentCount();
			}
		}
	}

	private void setButtonListeners(HorizontalLayout layout)
	{
		for (int i = 0; i < layout.getComponentCount(); ++i)
		{
			Component component = layout.getComponent(i);
			if (component instanceof Button) {
				Button button = (Button) component;
				if (button.getId().equals("DocumentSave")) {
					button.addClickListener(this.createSaveAction());
				} else if (button.getId().equals("DocumentTrash")) {
					button.addClickListener(this.createTrashAction());
				} else if (button.getId().equals("DocumentRun")) {
					button.addClickListener(this.createRunAction());
				}
			}
		}
	}

	private Button.ClickListener createTrashAction() {
		Button.ClickListener listener = new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (docId != null)
					mBackend.deleteDocument(docId);
				getUI().getNavigator().navigateTo("");
			}
		};

		return listener;
	}

	private Button.ClickListener createSaveAction() {
		Button.ClickListener listener = new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Document doc = mBackend.getDocument(docId);
				if (docId == null) {
					doc = new Document(mBackend.getCurrentUser(), docName.getValue(), docBody.getValue());
					docId = doc.getDocId();
				} else {
					doc = mBackend.getDocument(docId);
					doc.setDocName(docName.getValue());
					doc.setDocBody(docBody.getValue());
					doc.setModifyDate();
				}
				mBackend.saveDocument(doc);
			}
		};

		return listener;
	}

	private Button.ClickListener createRunAction() {
		Button.ClickListener listener = new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String result = mBackend.runProgram(docBody.getValue());
				Notification.show("Program Execution", result, Notification.Type.HUMANIZED_MESSAGE);
			}
		};
		return listener;
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
