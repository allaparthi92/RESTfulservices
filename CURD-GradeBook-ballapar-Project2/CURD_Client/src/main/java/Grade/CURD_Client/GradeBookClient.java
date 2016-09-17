package Grade.CURD_Client;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GradeBookClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeBookClient.class);
    
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8082/CURD_Server/webapi";
    static Converter converter = new Converter();

    public GradeBookClient() {        
        LOG.info("Creating a GradeBookClient ");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("GradeBook");
        LOG.debug("webResource = {}", webResource.getURI());
    }

    public ClientResponse createAppointment(Object requestEntity) throws UniformInterfaceException {
        LOG.info("Initiating a Create request");
        LOG.debug("XML String = {}", (String) requestEntity);
        
        return webResource.path("add").type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse deleteAppointment(Object requestEntity,String name) throws UniformInterfaceException, JAXBException {
    	
        LOG.info("Initiating a Delete request");
        LOG.debug("Id = {}", (String) requestEntity);
        
       if(name.equals("")){
    	   return webResource.path("iddelete/"+requestEntity).delete(ClientResponse.class);
       }
       else if(!name.equals("") && !requestEntity.equals("")){
    	   return webResource.path("idNamedelete/"+requestEntity+"/"+name).delete(ClientResponse.class);
       }
       else{
    	   return webResource.path("namedelete/"+name).delete(ClientResponse.class);
       }
    }

    public ClientResponse updateAppointment(Object requestEntity, String id) throws UniformInterfaceException {
        LOG.info("Initiating an Update request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);
        
        return webResource.path("updategrade").type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public <T> T retrieveAppointment(Class<T> responseType, String id,String name) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", id);
        
        //WebResource resource = webResource;
        //resource = resource.path(id);
        if(name.equals("")){
     	   return webResource.path("idretrieve/"+id).accept(MediaType.APPLICATION_XML).get(responseType);
        }
        else if(!id.equals("") && !name.equals("")){
     	   return webResource.path("idnameretrieve/"+id+"/"+name).accept(MediaType.APPLICATION_XML).get(responseType);
        }
        else{
     	   return webResource.path("nameretrieve/"+name).accept(MediaType.APPLICATION_XML).get(responseType);
        }
        
        
    }

    public void close() {
        LOG.info("Closing the REST Client");
        
        client.destroy();
    }
    
}
