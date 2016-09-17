package appealclient;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.research.ws.wadl.Application;

import appealmodels.Appeal;
import appealmodels.MessageContent;
import appealmodels.StatusofAppeal;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;
import appealrepresentations.Link;

public class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    private static final String APPEAL_MEDIA_TYPE = "application/vnd.appeals+xml";
    private static final long ONE_MINUTE = 60000; 
    
    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEOASAppealServer/webresources/appeal";

    public static void main(String[] args) throws Exception {
        URI serviceUri = new URI(ENTRY_POINT_URI);
        happyPathTest(serviceUri);
        Abadoncase(serviceUri);
        FollowupCase(serviceUri);
        BADURICase(serviceUri);
        BADIDCase(serviceUri);
    }

    private static void hangAround(long backOffTimeInMillis) {
        try {
            Thread.sleep(backOffTimeInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void happyPathTest(URI serviceUri) throws Exception {
        System.out.println("\n Starting Happy Path Test with Appeal URI ," + serviceUri );
      
        System.out.println("---------------  Placing a new appeal  ---------------------");
        
        System.out.println(String.format("\n About to start happy path test. Placing appeal at [%s] via POST", serviceUri.toString()));
        
        MessageContent msg = new MessageContent();
        msg.setUser("Bhanu Teja");
		msg.setContent("I have some mistake in my grade.Please review my grade again");
		ArrayList<MessageContent> al = new ArrayList<>(); 
		al.add(msg);
		Appeal order = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.CREATED.toString());
		
		System.out.println("\n Student "+ order.getStudentName()+ " requests for an appeal");
		
		//Student creates an appeal
        Client client = Client.create();      
        AppealRepresentation orderRepresentation = client.resource(serviceUri).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(order));
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", orderRepresentation, orderRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("\n Appeal placed at [%s]", orderRepresentation.getSelfLink().getUri().toString()));
        Appeal appeal1 =  orderRepresentation.getOrder();
        System.out.println("\n Appeal is created by "+appeal1.getStudentName()+" and the status is now "+ orderRepresentation.getStatus());
      
        
        // Professor updates the appeal
        System.out.println("\n Professor received the appeal and updating it");
        Link updtelink = orderRepresentation.getSelfLink();
        MessageContent profmsg = new MessageContent();
        profmsg.setUser("Callis");
        profmsg.setContent("Professor have received your appeal and reviewing your grade");
		ArrayList<MessageContent> Profal = new ArrayList<>(); 
		Profal.add(profmsg);
        Appeal appeal = new Appeal(1,"Callis","MidTerm-1",Profal,StatusofAppeal.AppealStatus.INPROCESS.toString());
        AppealRepresentation updateresponse = client.resource(updtelink.getUri()).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.info("updated AppealRepresentation {} denoted by the URI {}", updateresponse, updateresponse.getSelfLink().getUri().toString());
        Appeal appeal2 =  updateresponse.getOrder();
        System.out.println("Appeal Status is updated and changed to " + updateresponse.getStatus()+" by "+appeal.getStudentName());
       
   
        //  Professor updates the appeal
        System.out.println("\n Professor approved the appeal and updated the grade");    
        MessageContent profmsg1 = new MessageContent();
        profmsg1.setUser("Callis");
        profmsg1.setContent("Professor approved the appeal and updated the grade");
		ArrayList<MessageContent> Profal1 = new ArrayList<>(); 
		Profal1.add(profmsg1);
        Appeal appeal3 = new Appeal(101,"Callis","MidTerm-1",Profal1,StatusofAppeal.AppealStatus.APPROVED.toString());
        AppealRepresentation updateresponse3 = client.resource(updtelink.getUri()).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal3));
        LOG.info("updated AppealRepresentation {} denoted by the URI {}", updateresponse3, updateresponse3.getSelfLink().getUri().toString());
        Appeal appeal4 =  updateresponse3.getOrder();
        System.out.println("Appeal Status is updated and changed to " + updateresponse3.getStatus()+" by "+appeal3.getStudentName());
        
        
        //  Professor updates the appeal
        System.out.println("\n Professor Closing the appeal");    
        MessageContent profmsg2 = new MessageContent();
        profmsg2.setUser("Callis");
        profmsg2.setContent("Professor closing your appeal request");
		ArrayList<MessageContent> Profal2 = new ArrayList<>(); 
		Profal2.add(profmsg2);
        Appeal appeal5 = new Appeal(101,"Callis","MidTerm-1",Profal2,StatusofAppeal.AppealStatus.CLOSED.toString());
       // ClientResponse updateresponse5 = client.resource(updtelink.getUri()).accept(updtelink.getMediaType()).type(updtelink.getMediaType()).post(ClientResponse.class, new ClientAppeal(appeal5));
        AppealRepresentation updateresponse5 = client.resource(updtelink.getUri()).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal5));
        LOG.info("updated AppealRepresentation {} denoted by the URI {}", updateresponse5, updateresponse5.getSelfLink().getUri().toString());
        Appeal appeal7 =  updateresponse5.getOrder();
        System.out.println("Appeal Status is updated and changed to " + updateresponse5.getStatus()+" by "+appeal5.getStudentName());
        
       
    }
    
    private static void Abadoncase(URI serviceUri) throws Exception {
    	
    	 System.out.println("\n Starting Abadoncase Test with Appeal URI ," + serviceUri );
         
         System.out.println("---------------  Placing a new appeal  ---------------------");
         
         System.out.println(String.format("\n About to start Abadoncase test. Placing appeal at [%s] via POST", serviceUri.toString()));
         
         MessageContent msg = new MessageContent();
         msg.setUser("Bhanu Teja");
 		msg.setContent("I have some mistake in my grade.Please review my grade again");
 		ArrayList<MessageContent> al = new ArrayList<>(); 
 		al.add(msg);
 		Appeal order = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.CREATED.toString());
 		
 		System.out.println("\n Student "+ order.getStudentName()+ " requests for an appeal");
 		
 		//Student creates an appeal
         Client client = Client.create();      
         AppealRepresentation orderRepresentation = client.resource(serviceUri).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(order));
         LOG.debug("Created AppealRepresentation {} denoted by the URI {}", orderRepresentation, orderRepresentation.getSelfLink().getUri().toString());
         System.out.println(String.format("\n Appeal placed at [%s]", orderRepresentation.getSelfLink().getUri().toString()));
         Appeal appeal1 =  orderRepresentation.getOrder();
         System.out.println("\n Appeal is created by "+appeal1.getStudentName()+" and the status is now "+ orderRepresentation.getStatus());
       
         // Try to delete an appeal
         System.out.println("\n Srudent deleting the appeal");   
         Link updtelink = orderRepresentation.getSelfLink();
          AppealRepresentation updateresponse = client.resource(updtelink.getUri()).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).delete(AppealRepresentation.class);
        // LOG.info("updated AppealRepresentation {} denoted by the URI {}", updateresponse, updateresponse.getSelfLink().getUri().toString());
         Appeal appeal2 =  updateresponse.getOrder();
         System.out.println("Appeal  is Deleted and status  changed to " + updateresponse.getStatus()+" by "+appeal1.getStudentName());
       
         
    }
    
    private static void FollowupCase(URI serviceUri) throws Exception {
    	

    	System.out.println("\n Starting FollowupCase Test with Appeal URI ," + serviceUri );
        
        System.out.println("---------------  Placing a new appeal  ---------------------");
        
        System.out.println(String.format("\n About to start FollowupCase test. Placing appeal at [%s] via POST", serviceUri.toString()));
        
        MessageContent msg = new MessageContent();
        msg.setUser("Bhanu Teja");
		msg.setContent("I have some mistake in my grade.Please review my grade again");
		ArrayList<MessageContent> al = new ArrayList<>(); 
		al.add(msg);
		Appeal order = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.CREATED.toString());
		
		System.out.println("\n Student "+ order.getStudentName()+ " requests for an appeal");
		
		//Student creates an appeal
        Client client = Client.create();      
        AppealRepresentation orderRepresentation = client.resource(serviceUri).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(order));
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", orderRepresentation, orderRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("\n Appeal placed at [%s]", orderRepresentation.getSelfLink().getUri().toString()));
        Appeal appeal1 =  orderRepresentation.getOrder();
        System.out.println("\n Appeal is created by "+appeal1.getStudentName()+" and the status is now "+ orderRepresentation.getStatus());
      
        // Try to followup an appeal
        
        // Try to update a appeal
        System.out.println("\n Sudent trying to followup the appeal");   
        Link updtelink = orderRepresentation.getSelfLink();
        MessageContent msg2 = new MessageContent();
        msg2.setUser("Bhanu Teja");
        msg2.setContent("Requets for a followup");

		ArrayList<MessageContent> al2 = new ArrayList<>(); 
		al2.add(msg2);

		System.out.println("Student "+ order.getStudentName()+ " requests for a followup");
        Appeal appeal = new Appeal(101,"Bhanu Teja","MidTerm-1",al2,StatusofAppeal.AppealStatus.FOLLOWUP.toString());
        AppealRepresentation updateresponse = client.resource(updtelink.getUri()).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        LOG.info("updated AppealRepresentation {} denoted by the URI {}", updateresponse, updateresponse.getSelfLink().getUri().toString());
        Appeal appeal2 =  updateresponse.getOrder();
        System.out.println("Appeal Status is updated and changed to " + updateresponse.getStatus()+" by "+appeal.getStudentName());
      
   }
    private static void BADURICase (URI serviceUri) throws Exception {
    	
    	System.out.println("\n Starting BADURICase Test with Appeal URI ," + serviceUri );
        
        System.out.println("---------------  Placing a new appeal  ---------------------");
        
        System.out.println(String.format("\n About to start BADURICase test. Placing appeal at [%s] via POST", serviceUri.toString()));
        
        MessageContent msg = new MessageContent();
        msg.setUser("Bhanu Teja");
		msg.setContent("I have some mistake in my grade.Please review my grade again");
		ArrayList<MessageContent> al = new ArrayList<>(); 
		al.add(msg);
		Appeal order = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.CREATED.toString());
		
		System.out.println("\n Student "+ order.getStudentName()+ " requests for an appeal");
		
		//Student creates an appeal
        Client client = Client.create();      
        AppealRepresentation orderRepresentation = client.resource(serviceUri).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(order));
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", orderRepresentation, orderRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("\n Appeal placed at [%s]", orderRepresentation.getSelfLink().getUri().toString()));
        Appeal appeal1 =  orderRepresentation.getOrder();
        System.out.println("\n Appeal is created by "+appeal1.getStudentName()+" and the status is now "+ orderRepresentation.getStatus());
      
    	
	    System.out.println(String.format("About to update an appeal with bad URI [%s] via POST", orderRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
	    Appeal order1 = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.INPROCESS.toString());
	    LOG.debug("Created base order {}", order1);
	    Link badLink = new Link("bad", new AppealUri(orderRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), APPEAL_MEDIA_TYPE);
	    LOG.debug("Create bad link {}", badLink);
	    ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new ClientAppeal(order));
	    LOG.debug("Created Bad Update Response {}", badUpdateResponse);
	    System.out.println(String.format("Tried to update appeal with bad URI at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
    }
    
private static void BADIDCase (URI serviceUri) throws Exception {
    	
    	System.out.println("\n Starting BADIDCase Test with Appeal URI ," + serviceUri );
        
        System.out.println("---------------  Placing a new appeal  ---------------------");
        
        System.out.println(String.format("\n About to start BADIDCase test. Placing appeal at [%s] via POST", serviceUri.toString()));
        
        MessageContent msg = new MessageContent();
        msg.setUser("Bhanu Teja");
		msg.setContent("I have some mistake in my grade.Please review my grade again");
		ArrayList<MessageContent> al = new ArrayList<>(); 
		al.add(msg);
		Appeal order = new Appeal(101,"Bhanu Teja","MidTerm-1",al,StatusofAppeal.AppealStatus.CREATED.toString());
		
		System.out.println("\n Student "+ order.getStudentName()+ " requests for an appeal");
		
		//Student creates an appeal
        Client client = Client.create();      
        AppealRepresentation orderRepresentation = client.resource(serviceUri).accept(APPEAL_MEDIA_TYPE).type(APPEAL_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(order));
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", orderRepresentation, orderRepresentation.getSelfLink().getUri().toString());
        System.out.println(String.format("\n Appeal placed at [%s]", orderRepresentation.getSelfLink().getUri().toString()));
        Appeal appeal1 =  orderRepresentation.getOrder();
        System.out.println("\n Appeal is created by "+appeal1.getStudentName()+" and the status is now "+ orderRepresentation.getStatus());
      
    	
	    System.out.println(String.format("About to Retreive an appeal with bad ID  via GET"));
	    Link badLink = new Link("bad", new AppealUri(orderRepresentation.getSelfLink().getUri().toString() + "12345"), APPEAL_MEDIA_TYPE);
	    LOG.debug("Create bad link {}", badLink);
	    ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).get(ClientResponse.class);
	    LOG.debug("Created Bad Update Response {}", badUpdateResponse);
	    System.out.println(String.format("Tried to Retrieve appeal with bad ID at [%s] via GET, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
    }
}
