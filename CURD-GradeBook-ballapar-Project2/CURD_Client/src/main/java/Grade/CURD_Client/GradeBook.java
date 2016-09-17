package Grade.CURD_Client;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="student")
public class GradeBook {
	
	    private static final Logger LOG = LoggerFactory.getLogger(GradeBook.class);	 
	    private Integer gradebookid;
	    private Integer studentid;
	    private String studentname;
	    private String assignemntname;
	    private String assignemnttype;
	    private Integer grade;
	    private String feedback;
	   
	    
	    
	    public GradeBook() {
	        LOG.info("Creating an GradeBook object");
	    }
	  

		public GradeBook(Integer gradebookid, Integer studentid,
				String studentname, String assignemntname,
				String assignemnttype, Integer grade, String feedback) {
			super();
			this.gradebookid = gradebookid;
			this.studentid = studentid;
			this.studentname = studentname;
			this.assignemntname = assignemntname;
			this.assignemnttype = assignemnttype;
			this.grade = grade;
			this.feedback = feedback;
		}


		public Integer getStudentid() {
			return studentid;
		}

		public void setStudentid(Integer studentid) {
			this.studentid = studentid;
		}
		
		public String getStudentname() {
			return studentname;
		}

		public void setStudentname(String studentname) {
			this.studentname = studentname;
		}

		public Integer getGrade() {
			return grade;
		}

		public void setGrade(Integer grade) {
			this.grade = grade;
		}

		public String getFeedback() {
			return feedback;
		}

		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}

		public String getAssignemntname() {
			return assignemntname;
		}

		public void setAssignemntname(String assignemntname) {
			this.assignemntname = assignemntname;
		}

		public String getAssignemnttype() {
			return assignemnttype;
		}

		public void setAssignemnttype(String assignemnttype) {
			this.assignemnttype = assignemnttype;
		}

		public Integer getGradebookid() {
			return gradebookid;
		}

		public void setGradebookid(Integer gradebookid) {
			this.gradebookid = gradebookid;
		}
		
	public static void main(String args[]) throws JAXBException{
		Converter converter = new Converter();
		GradeBook gb = new GradeBook(1,101,"Bhanu Teja","Assignment","CURD",80,"good");
		String XMLInput = converter.convertFromObjectToXml(gb, GradeBook.class);
			
		System.out.println(XMLInput);
	}
}