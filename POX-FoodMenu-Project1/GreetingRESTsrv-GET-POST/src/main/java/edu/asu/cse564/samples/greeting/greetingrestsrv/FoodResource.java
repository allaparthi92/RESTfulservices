package edu.asu.cse564.samples.greeting.greetingrestsrv;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("foodItem")
public class FoodResource {

    private static final Logger LOG = LoggerFactory.getLogger(FoodResource.class);
    
    private String greeting;
  
    public FoodResource() {
        LOG.info("Creating a Food Resource");
    }

    @SuppressWarnings("unchecked")
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response postTextGreeting(String content) throws TransformerFactoryConfigurationError, Exception {
       
    	LOG.info("Creating the FoodItem");
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        if(!content.contains("/FoodItem")){
        	String response1 = "<InvalidMessage xmlns=”http://cse564.asu.edu/PoxAssignment”/>";
        	Response response = Response.status(Response.Status.OK).entity(new String(response1)).build();
            return response;
        }
        
        if(content.contains("SelectedFoodItems")){
        	List<String> RetrieveList = new ArrayList<String>();
        	FoodItemService foodService  = new FoodItemService();
        	RetrieveList = foodService.FoodItemServiceXMLStringRetrieve(content);
        	String res = foodService.RetreivedListXML(RetrieveList);
        	System.out.println(res);
        	Response response = Response.status(Response.Status.OK).entity(new String(res)).build();
        	return response;
        }
        List<FoodItem> foodlist = new ArrayList<FoodItem>();
       FoodItemService foodService  = new FoodItemService();
       foodlist  = foodService.FoodItemServiceXMLString(content);
       String responseContent= foodService.addToExistingXml(foodlist);
       LOG.info("response---"+ responseContent);
       Response response = Response.status(Response.Status.OK).entity(new String(responseContent)).build();
       return response;
    }
}
