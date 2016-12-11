package cirrus.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Document {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private int docId;
	private String docName;
	private String docBody;
	
	@ManyToOne
	private User docOwner;
	
	public Document(String docName, User ownerUserName) {
		this.docName = docName;
		this.docOwner = ownerUserName;
		this.docBody = "";
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public void setDocBody(String docBody) {
		this.docBody = docBody;
	}

	public User getDocOwner(){
		return docOwner;
	}

	public String getDocBody() {
		return docBody;
	}

	public void setDocOwner(User docOwner) {
		this.docOwner = docOwner;
	}

	@Override
	public String toString() {
		return "Document [docId=" + docId + ", docName=" + docName + ", docBody=" + docBody + ", docOwner=" + docOwner
				+ "]";
	}




}
