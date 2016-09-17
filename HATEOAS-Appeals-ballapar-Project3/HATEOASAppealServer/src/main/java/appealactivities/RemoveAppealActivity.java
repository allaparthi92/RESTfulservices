package appealactivities;

import appealmodels.Appeal;
import appealmodels.Identifier;
import appealrepository.RepositoryAppeal;
import appealrepresentations.AppealRepresentation;
import appealrepresentations.AppealUri;

public class RemoveAppealActivity {
    public AppealRepresentation delete(AppealUri orderUri) {
        // Discover the URI of the order that has been cancelled
        
        Identifier identifier = orderUri.getId();

        RepositoryAppeal orderRepository = RepositoryAppeal.current();

        if (orderRepository.orderNotPlaced(identifier)) {
            throw new NoSuchOrderException();
        }

        Appeal order = orderRepository.get(identifier);

        // Can't delete a ready or preparing order
        if (!order.getStatus().equals("SUBMITTED")) {
            throw new OrderDeletionException();
        }

        if(order.getStatus().equals("SUBMITTED")) { 
            orderRepository.remove(identifier);
            order.setStatus("ABANDONED");
        }

        return new AppealRepresentation(order);
    }

}
