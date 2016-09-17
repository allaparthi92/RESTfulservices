package Grade.CURD_Server;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerFactoryConfigurationError;



@Path("GradeBook")
public class GradeBookResource {
	
	/***create***/
	@Context
	private UriInfo context;
	static Converter converter = new Converter();
	@POST
	@Path("add")
    @Consumes(MediaType.APPLICATION_XML)
    public Response postAddStudentgrade(String content) throws TransformerFactoryConfigurationError, Exception {
	Response response ;
	String result = GraderBookService.AddStudentdetails(content); 
	GradeBook gb = (GradeBook) converter.convertFromXmlToObject(content,GradeBook.class);

	if(result.equals("")){
		response = Response.status(Response.Status.CONFLICT).build();
	}
	else{
		  response = Response.created(context.getBaseUriBuilder().path("GradeBook/idnameretrieve/"+gb.getStudentid()+"/"+gb.getAssignemntname()).build()).entity(result).build();
	}
	return response;
   
   
 }
	
	/****Retrieve******/
	@GET
	@Path("idretrieve/{id}")
	public Response GetStudentgrade(@PathParam("id")  Integer StudentId) throws TransformerFactoryConfigurationError, Exception {		
		Response response = null;
		String result = GraderBookService.SearchById(StudentId);
		
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
	   response = Response.created(context.getBaseUriBuilder().path("GradeBook/idretrieve/"+StudentId).build()).entity(result).build();

		}
		return response;

	}
	@GET
	@Path("idnameretrieve/{id}/{assignmentname}")
	public Response GetStudentgradeBYIDName(@PathParam("id")  Integer StudentId,@PathParam("assignmentname")  String AssignemnetName) throws TransformerFactoryConfigurationError, Exception {		
		Response response = null;
		String result = GraderBookService.SearchByIdName(StudentId,AssignemnetName);
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
		     response =  Response.status(Response.Status.OK).entity(result).build();
		}
		return response;

	}
	@GET
	@Path("nameretrieve/{assignmentname}")
	public Response GetStudentgradeByName(@PathParam("assignmentname")  String AssignemnetName) throws TransformerFactoryConfigurationError, Exception {		
		Response response = null;
		String result = GraderBookService.SearchByName(AssignemnetName);
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
			 response = Response.created(context.getBaseUriBuilder().path("GradeBook/nameretrieve/"+AssignemnetName).build()).entity(result).build();

		}
		return response;

	}
	
	
	@DELETE
	@Path("iddelete/{id}")
	public Response DeleteById(@PathParam("id")  Integer Id) throws TransformerFactoryConfigurationError, Exception {		
		Response response = null;
		String result = GraderBookService.deleteByID(Id);
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
		     response =  Response.status(Response.Status.OK).entity(result).build();
		}
		return response;

	}
	
	@DELETE
	@Path("idNamedelete/{id}/{assignmentname}")
	public Response DeleteByNameID(@PathParam("id")  Integer Id,@PathParam("assignmentname")  String AssignemnetName) throws TransformerFactoryConfigurationError, Exception {		
		Response response = null;
		String result = GraderBookService.deleteByIDName(Id,AssignemnetName);
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
		     response =  Response.status(Response.Status.OK).entity(result).build();
		}
		return response;

	}
	
	@DELETE
	@Path("namedelete/{assignmentname}")
	public Response DeleteByAssignment(@PathParam("assignmentname")  String AssignemnetName) throws TransformerFactoryConfigurationError, Exception {		
		Response response ;
		String result = GraderBookService.deleteByName(AssignemnetName);
		
		if(result.equals("")){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
		     response =  Response.status(Response.Status.OK).entity(result).build();
		}
		return response;

	}
	@PUT
	@Path("updategrade")
	public Response UpdateMarks(String Content) throws TransformerFactoryConfigurationError, Exception {		
		
		Response response = null;
		String result = GraderBookService.UpdateById(Content);
		
		GradeBook gb = (GradeBook) converter.convertFromXmlToObject(Content,GradeBook.class);
	  
		if(result == null){
			response = Response.status(Response.Status.NOT_FOUND).build();
		}
		else{
			 response = Response.created(context.getBaseUriBuilder().path("GradeBook/idnameretrieve/"+gb.getStudentid()+"/"+gb.getAssignemntname()).build()).entity(result).build();

		}
		return response;

	}
	
}


