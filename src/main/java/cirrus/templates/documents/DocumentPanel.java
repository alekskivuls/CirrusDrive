package cirrus.templates.documents;

import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;

public class DocumentPanel extends Panel {
	TextArea docBody;

	public DocumentPanel() {
		docBody = new TextArea();
		docBody.setWordwrap(false);
		docBody.setSizeFull();
		setSizeFull();
		setContent(docBody);
	}
	
	public String getDocBody() {
		return docBody.getValue();
	}
	
	public void setDocBody(String text) {
		docBody.setValue(text);
	}
}
