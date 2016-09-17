package appealactivities;

import appealmodels.Appeal;
import appealmodels.Identifier;
import appealmodels.StatusofAppeal;
import appealrepository.RepositoryAppeal;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;
import appealrepresentations.Link;
import appealrepresentations.Representation;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal order, AppealUri requestUri) {
        order.setStatus(StatusofAppeal.AppealStatus.SUBMITTED.toString());
                
        Identifier identifier = RepositoryAppeal.current().store(order);
        
        AppealUri orderUri = new AppealUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
         
        return new AppealRepresentation(order, 
                new Link(Representation.RELATIONS_URI + "cancel", orderUri), 
                new Link(Representation.RELATIONS_URI + "update", orderUri),
                new Link(Representation.SELF_REL_VALUE, orderUri));
    
}
}
