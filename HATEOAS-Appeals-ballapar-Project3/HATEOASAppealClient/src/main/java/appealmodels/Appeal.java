package appealmodels;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="appeal")
public class Appeal {
	private static final Logger LOG = LoggerFactory.getLogger(Appeal.class);	
	private Integer studentID;
	private String studentName;
	private String taskName;
	public ArrayList<MessageContent> message;
	private String status = "CREATED";
	
	 public Appeal() {
	        LOG.info("Creating an Appeal object");
	    }
	  
	
	public Appeal(Integer studentID, String studentName, String taskName, ArrayList<MessageContent> message,
			String status) {
		super();
		this.studentID = studentID;
		this.studentName = studentName;
		this.taskName = taskName;
		this.message = message;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ArrayList<MessageContent> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<MessageContent> message) {
		this.message = message;
	}
	
	public static void main(String args[]) throws JAXBException{
		Converter converter = new Converter();
		MessageContent msg = new MessageContent();
		msg.setContent("request1");
		ArrayList<MessageContent> al = new ArrayList<>(); 
		al.add(msg);
		Appeal gb = new Appeal(101,"Bhanu Teja","Assignment1",al,"Created");
		String XMLInput = converter.convertFromObjectToXml(gb, Appeal.class);
			
		System.out.println(XMLInput);
	}
	
	
}
