package REST;

import Controller.BidderController;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/bidder")
public class BidderRest {

    @Inject
    BidderController bc;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBidder(BidderWrapper bidder) {
        bc.add(bc.unwrap(bidder));
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BidderWrapper getBidder(@PathParam("id") Long id) {
        return BidderWrapper.wrap(bc.get(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BidderWrapper> getBidders() {
        return BidderWrapper.wrap(bc.getAll());
    }
    
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BidderWrapper> getBiddersByName(@PathParam("name") String name) {
        return BidderWrapper.wrap(bc.get(name));
    }

}
