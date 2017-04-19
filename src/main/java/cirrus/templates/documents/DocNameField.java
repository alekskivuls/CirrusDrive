package cirrus.templates.documents;

import com.vaadin.ui.TextField;

public class DocNameField extends TextField {
	public TextField docName;

	public DocNameField() {
		docName = new TextField("");
	}

	public String getDocName() {
		return docName.getValue();
	}
	
	public void setDocName(String name) {
		System.out.println("Setting");
		docName.setValue(name);
		docName.setInputPrompt("test");
		System.out.println(docName.getValue());
	}

}
