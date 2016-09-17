package appealrepository;

import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import appealmodels.Appeal;
import appealmodels.Identifier;

public class RepositoryAppeal {
	
	    private static final Logger LOG = LoggerFactory.getLogger(RepositoryAppeal.class);

	    private static final RepositoryAppeal theRepository = new RepositoryAppeal();
	    private HashMap<String, Appeal> backingStore = new HashMap<>(); // Default implementation, not suitable for production!

	    public static RepositoryAppeal current() {
	        return theRepository;
	    }
	    
	    private RepositoryAppeal(){
	        LOG.debug("OrderRepository Constructor");
	    }
	    
	    public Appeal get(Identifier identifier) {
	        LOG.debug("Retrieving Appeal object for identifier {}", identifier);
	        return backingStore.get(identifier.toString());
	     }
	    
	    public Appeal take(Identifier identifier) {
	        LOG.debug("Removing the Apeal object for identifier {}", identifier);
	        Appeal order = backingStore.get(identifier.toString());
	        remove(identifier);
	        return order;
	    }

	    public Identifier store(Appeal order) {
	        LOG.debug("Storing a new Appeal object");
	                
	        Identifier id = new Identifier();
	        LOG.debug("New order object id is {}", id);
	                
	        backingStore.put(id.toString(), order);
	        return id;
	    }
	    
	    public void store(Identifier identifier, Appeal order) {
	        LOG.debug("Storing again the Appeal object with id", identifier);
	        backingStore.put(identifier.toString(), order);
	    }

	    public boolean has(Identifier identifier) {
	        LOG.debug("Checking to see if there is an Appeal object associated with the id {} in the Order store", identifier);
	        
	        boolean result =  backingStore.containsKey(identifier.toString());
	        LOG.debug("The result of the search is {}", result);
	        
	        return result;
	    }

	    public void remove(Identifier identifier) {
	        LOG.debug("Removing from storage the Appeal object with id", identifier);
	        backingStore.remove(identifier.toString());
	    }
	    
	    public boolean orderPlaced(Identifier identifier) {
	        LOG.debug("Checking to see if the Appeal with id = {} has been place", identifier);
	        return RepositoryAppeal.current().has(identifier);
	    }
	    
	    public boolean orderNotPlaced(Identifier identifier) {
	        LOG.debug("Checking to see if the Appeal with id = {} has not been place", identifier);
	        return !orderPlaced(identifier);
	    }
	    
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        for(Entry<String, Appeal> entry : backingStore.entrySet()) {
	            sb.append(entry.getKey());
	            sb.append("\t:\t");
	            sb.append(entry.getValue());
	            sb.append("\n");
	        }
	        return sb.toString();
	    }

	    public synchronized void clear() {
	        backingStore = new HashMap<>();
	    }

	    public int size() {
	        return backingStore.size();
	    }
	}


