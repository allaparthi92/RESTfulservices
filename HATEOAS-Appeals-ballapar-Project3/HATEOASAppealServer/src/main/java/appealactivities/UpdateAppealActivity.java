package appealactivities;

import java.util.ArrayList;

import appealmodels.Appeal;
import appealmodels.MessageContent;
import appealmodels.StatusofAppeal;
import appealrepository.RepositoryAppeal;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;

public class UpdateAppealActivity {
    public AppealRepresentation update(Appeal order, AppealUri orderUri) {
        appealmodels.Identifier orderIdentifier = orderUri.getId();

        RepositoryAppeal repository = RepositoryAppeal.current();
        if (RepositoryAppeal.current().orderNotPlaced(orderIdentifier)) { // Defensive check to see if we have the order
            throw new NoSuchOrderException();
        }

       
        Appeal storedOrder = repository.get(orderIdentifier);
        
        MessageContent Msgchain = new MessageContent(); 
        
        for(MessageContent msg : order.getMessage()){
        	Msgchain = msg;
        }
        

        if(RepositoryAppeal.current().get(orderIdentifier).getStatus().equals("SUBMITTED") && (order.getStatus().equals("FOLLOWUP")||
        		order.getStatus().equals("ABANDONED")||order.getStatus().equals("INPROCESS"))){
        	if(order.getStatus().equals("FOLLOWUP"))
	       	 	storedOrder.setStatus("SUBMITTED");
        	else
        		storedOrder.setStatus(order.getStatus());
	       	storedOrder.message.add(Msgchain);
       }
        
        else if(RepositoryAppeal.current().get(orderIdentifier).getStatus().equals("INPROCESS")&& (order.getStatus().equals("APPROVED")||
        		order.getStatus().equals("DECLINED"))){
        	 storedOrder.setStatus(order.getStatus());
 	         storedOrder.message.add(Msgchain);  	
        }          
        else  if(RepositoryAppeal.current().get(orderIdentifier).getStatus().equals("APPROVED") || RepositoryAppeal.current().get(orderIdentifier).getStatus().equals("DECLINED")
        					&& order.getStatus().equals("CLOSED")){
     
        	storedOrder.setStatus(order.getStatus());
	         storedOrder.message.add(Msgchain); 
        }
        else{
        	throw new UpdateException();
        }
       
      

        return AppealRepresentation.createResponseOrderRepresentation(storedOrder, orderUri); 
    }
    
    private boolean orderCanBeChanged(appealmodels.Identifier identifier) {
        return RepositoryAppeal.current().get(identifier).getStatus().equals(StatusofAppeal.AppealStatus.SUBMITTED.toString());
    }
}
