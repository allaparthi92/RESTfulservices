package appealclient;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import appealmodels.Appeal;
import appealmodels.MessageContent;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.Representation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.APPEAL_NAMESPACE)
public class ClientAppeal extends Representation{
    
    private static final Logger LOG = LoggerFactory.getLogger(ClientAppeal.class);
    

    @XmlElement(name = "studentID", namespace = Representation.APPEAL_NAMESPACE)
    private Integer studentID;
    @XmlElement(name = "studentName", namespace = Representation.APPEAL_NAMESPACE)
    private String studentName;
    @XmlElement(name = "taskName", namespace = Representation.APPEAL_NAMESPACE)
    private String taskName;
    @XmlElement(name = "message", namespace = Representation.APPEAL_NAMESPACE)
	private ArrayList<MessageContent> message;
    @XmlElement(name = "status", namespace = Representation.APPEAL_NAMESPACE)
   	private String status;

    private ClientAppeal(){}
    
    public ClientAppeal(Appeal order) {
        LOG.debug("Executing ClientAppeal constructor");
        this.studentID = order.getStudentID();
        this.studentName = order.getStudentName();
        this.taskName = order.getTaskName();
        this.status = order.getStatus();
        this.message=order.getMessage();
    }
    
    public Appeal getOrder() {
        LOG.debug("Executing ClientAppeal.getAppeal");
        Appeal order = new Appeal(studentID,studentName,taskName,message,status);
        return order;
    }
    
   

    @Override
    public String toString() {
        LOG.debug("Executing ClientOrder.toString");
        try {
            JAXBContext context = JAXBContext.newInstance(ClientAppeal.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getStatus() {
        LOG.debug("Executing ClientOrder.getStatus");
        return status;
    }

   
}