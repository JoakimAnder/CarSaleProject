package Entities;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BidTest {
    
    Bid b;
    
    public BidTest() {
    }
    @Before
    public void setup() {
        b = new Bid();
    }
    @Test
    public void addBidFromAuction() {
        b.setId(1L);
        Auction a = new Auction();
        a.setId(2L);
        
        a.addBid(b);
        
        assertEquals(a, b.getAuction());
        assertEquals(b, a.getBids().get(0));
    }
    @Test
    public void addBidFromBidder() {
        b.setId(1L);
        Bidder a = new Bidder();
        a.setId(2L);
        
        a.addBid(b);
        
        assertEquals(a.getId(), b.getBidder().getId());
        assertEquals(b.getId(), a.getBids().get(0).getId());
    }
    @Test(expected = IllegalArgumentException.class)
    public void setDifferentBidder() {
        Bidder bidder1 = new Bidder();
        Bidder bidder2 = new Bidder();
        bidder1.setId(2L);
        bidder2.setId(3L);
        b.setBidder(bidder1);
        
        b.setBidder(bidder2);
    }
    @Test
    public void setSameBidder() {
        Bidder a = new Bidder();
        a.setId(2L);
        
        b.setBidder(a);
        b.setBidder(a);
        
        assertEquals(a, b.getBidder());
    }
    @Test(expected = IllegalArgumentException.class)
    public void setDifferentAuction() {
        Auction a1 = new Auction();
        Auction a2 = new Auction();
        a1.setId(2L);
        a2.setId(3L);
        b.setAuction(a1);
        
        b.setAuction(a2);
    }
    @Test
    public void setSameAuction() {
        Auction a = new Auction();
        a.setId(2L);
        
        b.setAuction(a);
        b.setAuction(a);
        
        assertEquals(a, b.getAuction());
    }
    @Test
    public void setAuctionToNull() {
        Auction a = null;
        
        b.setAuction(a);
        
        assertNull(b.getAuction());
    }
    @Test
    public void setBidderToNull() {
        Bidder bidder = null;
        
        b.setBidder(bidder);
        
        assertNull(b.getBidder());
    }
    
}
