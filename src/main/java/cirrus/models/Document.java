package cirrus.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
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
	@Column(length=1000000)
	private String docBody;
	private String docDescription;
	private String createDate;
	private String modifyDate;
	
	@ManyToOne
	private User docOwner;
	
	protected Document() {
	}
	
	public Document(User ownerUserName, String docName) {
		setCreateDate();
		this.docName = docName;
		this.docOwner = ownerUserName;
		this.docBody = "";
	}
	
	public Document(User ownerUserName, String docName, String docBody) {
		setCreateDate();
		this.docName = docName;
		this.docOwner = ownerUserName;
		this.docBody = docBody;
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
	
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getDocDescription() {
		return docDescription;
	}
	
	public void setCreateDate() {
		this.createDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
	}
	
	public void setModifyDate() {
		this.modifyDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public String getModifyDate() {
		return modifyDate;
	}
	
	@Override
	public String toString() {
		return "Document [docId=" + docId + ", docName=" + docName + ", docBody=" + docBody + ", docOwner=" + docOwner
				+ "]";
	}
}
