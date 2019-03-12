package Entities;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BidderTest {
    
    Bidder b;
    
    public BidderTest() {
    }
    @Before
    public void setup() {
        b = new Bidder();
    }

    @Test
    public void addBidTest() {
        b.setId(1L);
        Bid bid = new Bid();
        bid.setId(2L);
        
        b.addBid(bid);
        
        assertEquals(b, bid.getBidder());
        assertEquals(bid, b.getBids().get(0));
    }
    @Test
    public void addBidToNulledListTest() {
        b.setBids(null);
        
        b.addBid(new Bid());
        
        assertEquals(1, b.getBids().size());
    }
    @Test(expected = NullPointerException.class)
    public void addNullToBidsTest() {
        b.addBid(null);
    }
    @Test
    public void removeBidTest() {
        Bid bid = new Bid();
        bid.setId(2L);
        b.addBid(bid);
        
        b.removeBid(bid);
        
        assertNull(bid.getBidder());
        assertEquals(0, b.getBids().size());
    }
    @Test
    public void removeBidFromNulledListTest() {
        b.setBids(null);
        
        b.removeBid(new Bid());
    }
    @Test
    public void getBidsCanNotBeNullTest() {
        b.setBids(null);
        
        assertNotNull(b.getBids());
    }
}
