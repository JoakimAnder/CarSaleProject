package REST;

import Controller.AuctionController;
import Entities.Auction;
import static REST.AuctionWrapper.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/auction")
public class AuctionRest {

    @Inject
    AuctionController ac;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAuction(AuctionWrapper auction) {
        ac.add(ac.unwrap(auction));
    }
    
    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public AuctionWrapper getAuction(@PathParam("id") Long id) {
        Auction a = ac.get(id);
        if (a == null)
            return null;
        return wrap(a);
    }
    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<AuctionWrapper> getAvaliableAuctions() {
        return wrap(ac.getAll());
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<AuctionWrapper> getAllAvaliableAuctions() {
        return wrap(ac.getAllAvaliable());
    }
    
    @GET
    @Path("/sold")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionWrapper> getSoldAuctions() {
        return wrap(ac.getAllSold());
    }
    
    @GET
    @Path("/end/{min}/{max}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionWrapper> getByEndTime(@PathParam("min") String min, @PathParam("max") String max) {
        LocalDateTime ldtmin = LocalDateTime.parse(min);
        LocalDateTime ldtmax = LocalDateTime.parse(max);
        return wrap(ac.getByEndTime(ldtmin, ldtmax));
    }
    
    @GET
    @Path("/{min}/{max}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionWrapper> getByHighestBid(@PathParam("min") int min, @PathParam("max") int max) {
        return wrap(ac.getByHighestBid(min, max, false));
    }
    
    @GET
    @Path("/sold/{min}/{max}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionWrapper> getSoldByHighestBid(@PathParam("min") int min, @PathParam("max") int max) {
        return wrap(ac.getByHighestBid(min, max, true));
    }
    
    @PUT
    @Path("/bid/{auctionId}/{bid}")
    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public boolean bid(BidderWrapper bidder, @PathParam("auctionId") Long auctionId, @PathParam("bid") int bid) {
        return ac.bid(ac.get(auctionId), ac.unwrap(bidder), bid);
    }
    
//    @GET
//    @Path("/bid/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)
//    public BidWrapper bid(@PathParam("id") Long id) {
//        return wrap(ac.getBid(id));
//    }
}
