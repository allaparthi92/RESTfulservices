package appealresources;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import appealactivities.*;
import appealactivities.InvalidAppealException;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;

@Path("/appeal")
public class AppealResource {
	
	    private static final Logger LOG = LoggerFactory.getLogger(AppealResource.class);

	    private @Context UriInfo uriInfo;

	    public AppealResource() {
	        LOG.info("AppealResource constructor");
	    }

	    public AppealResource(UriInfo uriInfo) {
	        LOG.info("AppealResource constructor with mock uriInfo {}", uriInfo);
	        this.uriInfo = uriInfo;  
	    }
	    
	    @GET
	    @Path("/{appealID}")
	    @Produces("application/vnd.appeals+xml")
	    public Response getOrder() {
	        LOG.info("Retrieving an Appeal Resource");
	        
	        Response response;
	        
	        try {
	            AppealRepresentation responseRepresentation = new appealactivities.ReadAppealActivity().retrieveByUri(new AppealUri(uriInfo.getRequestUri()));
	            
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch(NoSuchOrderException nsoe) {
	            LOG.debug("No such Appeal");
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch (Exception ex) {
	            LOG.debug("Something went wrong retriveing the Appeal");
	            response = Response.serverError().build();
	        }
	        
	        LOG.debug("Retrieved the Appeal resource", response);
	        
	        return response;
	    }
	    
	    @POST
	    @Consumes("application/vnd.appeals+xml")
	    @Produces("application/vnd.appeals+xml")
	    public Response createOrder(String orderRepresentation) {
	        LOG.info("Creating an Appeal Resource");
	        //System.out.println("String represntation" + orderRepresentation);
	        
	        Response response;
	        
	        try {
	            AppealRepresentation responseRepresentation = new CreateAppealActivity().create(AppealRepresentation.fromXmlString(orderRepresentation).getOrder(), new AppealUri(uriInfo.getRequestUri()));
	            response = Response.created(responseRepresentation.getUpdateLink().getUri()).entity(responseRepresentation).build();
	        } catch (InvalidAppealException ioe) {
	            LOG.debug("Invalid Appeal - Problem with the orderrepresentation {}", orderRepresentation);
	            response = Response.status(Status.BAD_REQUEST).build();
	        } catch (Exception ex) {
	            LOG.debug("Someting went wrong creating the Appeal resource");
	            response = Response.serverError().build();
	        }
	        
	        System.out.println("Resulting response for creating the Appeal resource is {}"+ response);
	        
	        return response;
	    }

	    @DELETE
	    @Path("/{orderId}")
	    @Produces("application/vnd.appeals+xml")
	    public Response removeOrder() {
	        LOG.info("Removing an appeal Reource");
	        
	        Response response;
	        
	        try {
	        	AppealRepresentation removedOrder = new RemoveAppealActivity().delete(new AppealUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(removedOrder).build();
	        } catch (NoSuchOrderException nsoe) {
	            LOG.debug("No such Appeal resource to delete");
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(OrderDeletionException ode) {
	            LOG.debug("Problem deleting Appeal resource");
	            response = Response.status((405)).header("Allow", "GET").build();
	        } catch (Exception ex) {
	            LOG.debug("Something went wrong deleting the Appeal resource");
	            response = Response.serverError().build();
	        }
	        
	        System.out.println("\n Appeal Resource is deleted \n");
	        
	        return response;
	    }

	    @POST
	    @Path("/{appealID}")
	    @Consumes("application/vnd.appeals+xml")
	    @Produces("application/vnd.appeals+xml")
	    public Response updateOrder(String orderRepresentation) {
	        System.out.println(" \n Updating an Appeal Resource \n");
	        
	        Response response;
	        
	        try {
	        	AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(orderRepresentation).getOrder(), new AppealUri(uriInfo.getRequestUri()));
	            response = Response.created(responseRepresentation.getSelfLink().getUri()).entity(responseRepresentation).build();
	        } catch (InvalidAppealException ioe) {
	            LOG.debug("Invalid Appeal in the XML representation {}", orderRepresentation);
	            response = Response.status(Status.BAD_REQUEST).build();
	        } catch (NoSuchOrderException nsoe) {
	            LOG.debug("No such Appeal resource to update");
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(UpdateException ue) {
	            LOG.debug("Problem updating the Appeal resource");
	            response = Response.status(Status.CONFLICT).build();
	        } catch (Exception ex) {
	            LOG.debug("Something went wrong updating the Appeal resource");
	            ex.printStackTrace();
	            response = Response.serverError().build();
	        } 
	        
	        System.out.println("Resulting response for updating the Appeal resource is {}" +response);
	        
	        return response;
	     }
	}


