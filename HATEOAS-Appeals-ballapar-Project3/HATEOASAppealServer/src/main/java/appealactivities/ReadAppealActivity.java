package appealactivities;

import appealmodels.Appeal;
import appealmodels.Identifier;
import appealrepository.RepositoryAppeal;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(AppealUri orderUri) {
        Identifier identifier  = orderUri.getId();
        
        Appeal order = RepositoryAppeal.current().get(identifier);
        
        if(order == null) {
            throw new NoSuchOrderException();
        }
        
        return AppealRepresentation.createResponseOrderRepresentation(order, orderUri);
    }
}
