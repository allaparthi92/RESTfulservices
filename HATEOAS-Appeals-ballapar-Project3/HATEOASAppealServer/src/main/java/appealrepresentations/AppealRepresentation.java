package appealrepresentations;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import appealactivities.InvalidAppealException;
import appealmodels.Appeal;
import appealmodels.MessageContent;
import appealmodels.StatusofAppeal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.APPEAL_NAMESPACE)
public class AppealRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);

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


	/**
     * For JAXB :-(
     */
    AppealRepresentation() {
        LOG.debug("In OrderRepresentation Constructor");
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
        LOG.info("Creating an Order object from the XML = {}", xmlRepresentation);
                
        AppealRepresentation orderRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            orderRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
        	e.printStackTrace();
            throw new InvalidAppealException(e);
        }
        
        LOG.debug("Generated the object {}", orderRepresentation);
        return orderRepresentation;
    }
    
    public static AppealRepresentation createResponseOrderRepresentation(Appeal order, AppealUri orderUri) {
        LOG.info("Creating a Response Order for order = {} and order URI", order.toString(), orderUri.toString());
        
        AppealRepresentation orderRepresentation = null;     
        
        if(order.getStatus().toString().equals(StatusofAppeal.AppealStatus.CREATED.toString())) {
        	
            LOG.debug("The Appeal status is {}", StatusofAppeal.AppealStatus.CREATED);
            orderRepresentation = new AppealRepresentation(order, 
                    new Link(RELATIONS_URI + "cancel", orderUri), 
                    new Link(RELATIONS_URI + "update", orderUri),
                    new Link(Representation.SELF_REL_VALUE, orderUri));
            LOG.debug("The Appeal representation created for the Create Response Order Representation is {}", orderRepresentation);
        }
        else if (order.getStatus().toString().equals(StatusofAppeal.AppealStatus.INPROCESS.toString())){
        	 LOG.info("The Updated Appeal status is moved to {}", StatusofAppeal.AppealStatus.INPROCESS);
        	 
        	 orderRepresentation = new AppealRepresentation(order, new Link(Representation.SELF_REL_VALUE, orderUri));
        }
        
        else if (order.getStatus().toString().equals(StatusofAppeal.AppealStatus.APPROVED.toString())){
       	 LOG.info("The Updated Appeal status is moved to {}", StatusofAppeal.AppealStatus.APPROVED);
       	 
       	 orderRepresentation = new AppealRepresentation(order, new Link(Representation.SELF_REL_VALUE, orderUri));
       }
        else  if (order.getStatus().toString().equals(StatusofAppeal.AppealStatus.SUBMITTED.toString())){
       	 
       	 
       	 orderRepresentation = new AppealRepresentation(order, new Link(Representation.SELF_REL_VALUE, orderUri));
       }
        else if (order.getStatus().toString().equals(StatusofAppeal.AppealStatus.CLOSED.toString())){
        	
        	orderRepresentation = new AppealRepresentation(order, new Link(Representation.SELF_REL_VALUE, orderUri));
        	
        }
        
      
        
        return orderRepresentation;
    }

    public AppealRepresentation(Appeal order, Link... links) {
        LOG.info("Creating an Appeal Representation for Appeal = {} and links = {}", order.toString(), links.toString());
        
        try {
            this.studentID = order.getStudentID();
            this.studentName = order.getStudentName();
            this.taskName = order.getTaskName();
            this.status = order.getStatus();
            this.message=order.getMessage();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
        LOG.debug("Created the AppealRepresentation {}", this);
    }

    @Override
    public String toString() {
        LOG.info("Converting Order Representation object to string");
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getOrder() {
        LOG.info("Retrieving the Appeal Representation");
       
        if (studentName == null) {
            throw new InvalidAppealException();
        }
        if (studentID == null) {
            throw new InvalidAppealException();
        }
        if (taskName == null) {
            throw new InvalidAppealException();
        }        
        Appeal order = new Appeal(studentID,studentName,taskName,message,status);
        
        LOG.debug("Retrieving the Appeal Representation {}", order);

        return order;
    }

    public Link getCancelLink() {
      //  LOG.info("Retrieving the Cancel link ");
        return getLinkByName(RELATIONS_URI + "cancel");
    }

    public Link getUpdateLink() {
        //LOG.info("Retrieving the Update link ");
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
       // LOG.info("Retrieving the Self link ");
        return getLinkByName("self");
    }
    
    public String getStatus() {
        //LOG.info("Retrieving the order status {}", status);
        return status;
    }
  

}
