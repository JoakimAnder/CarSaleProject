package Entities;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AuctionTest {
    
    Auction a;
    
    public AuctionTest() {
    }
    
    @Before
    public void setup() {
        a = new Auction();
    }
    
    @Test
    public void addBidTest() {
        Bid b = new Bid();
        
        a.addBid(b);
        
        assertEquals(Collections.singletonList(b), a.getBids());
        assertEquals(a, b.getAuction());
    }
    @Test
    public void getBidsNotNullTest() {
        
        List<Bid> list = a.getBids();
        
        assertNotNull(list);
    }
    @Test
    public void getHighestBidTest() {
        Bid b1 = new Bid();
        Bid b2 = new Bid();
        Bid b3 = new Bid();
        b1.setAmount(199);
        b2.setAmount(200);
        b3.setAmount(201);
        Auction a1 = new Auction();
        Auction a2 = new Auction();
        Auction a3 = new Auction();
        Auction a4 = new Auction();
        Auction a5 = new Auction();
        Auction a6 = new Auction();
        a1.setBids(Arrays.asList(b1,b2,b3));
        a2.setBids(Arrays.asList(b1,b3,b2));
        a3.setBids(Arrays.asList(b2,b1,b3));
        a4.setBids(Arrays.asList(b2,b3,b1));
        a5.setBids(Arrays.asList(b3,b1,b2));
        a6.setBids(Arrays.asList(b3,b2,b1));
        
        
        assertEquals(b3.getAmount(), a1.getHighestBid());
        assertEquals(b3.getAmount(), a2.getHighestBid());
        assertEquals(b3.getAmount(), a3.getHighestBid());
        assertEquals(b3.getAmount(), a4.getHighestBid());
        assertEquals(b3.getAmount(), a5.getHighestBid());
        assertEquals(b3.getAmount(), a6.getHighestBid());
        
    }
    @Test
    public void getLatestBidTest() {
        Bid b1 = new Bid();
        Bid b2 = new Bid();
        Bid b3 = new Bid();
        b1.setTimeOfBid(LocalDateTime.MIN);
        b2.setTimeOfBid(LocalDateTime.now());
        b3.setTimeOfBid(LocalDateTime.MAX);
        
        Auction a1 = new Auction();
        Auction a2 = new Auction();
        Auction a3 = new Auction();
        Auction a4 = new Auction();
        Auction a5 = new Auction();
        Auction a6 = new Auction();
        a1.setBids(Arrays.asList(b1,b2,b3));
        a2.setBids(Arrays.asList(b1,b3,b2));
        a3.setBids(Arrays.asList(b2,b1,b3));
        a4.setBids(Arrays.asList(b2,b3,b1));
        a5.setBids(Arrays.asList(b3,b1,b2));
        a6.setBids(Arrays.asList(b3,b2,b1));
        
        
        assertEquals(b3.getTimeOfBid(), a1.getLatestBid());
        assertEquals(b3.getTimeOfBid(), a2.getLatestBid());
        assertEquals(b3.getTimeOfBid(), a3.getLatestBid());
        assertEquals(b3.getTimeOfBid(), a4.getLatestBid());
        assertEquals(b3.getTimeOfBid(), a5.getLatestBid());
        assertEquals(b3.getTimeOfBid(), a6.getLatestBid());
    }
    @Test
    public void getLatestBidderTest() {
        Bidder bi1 = new Bidder();
        Bidder bi2 = new Bidder();
        Bidder bi3 = new Bidder();
        bi1.setId(1L);
        bi2.setId(2L);
        bi3.setId(3L);
        Bid b1 = new Bid();
        Bid b2 = new Bid();
        Bid b3 = new Bid();
        b1.setTimeOfBid(LocalDateTime.MIN);
        b2.setTimeOfBid(LocalDateTime.now());
        b3.setTimeOfBid(LocalDateTime.MAX);
        b1.setBidder(bi1);
        b2.setBidder(bi2);
        b3.setBidder(bi3);
        Auction a1 = new Auction();
        Auction a2 = new Auction();
        Auction a3 = new Auction();
        Auction a4 = new Auction();
        Auction a5 = new Auction();
        Auction a6 = new Auction();
        a1.setBids(Arrays.asList(b1,b2,b3));
        a2.setBids(Arrays.asList(b1,b3,b2));
        a3.setBids(Arrays.asList(b2,b1,b3));
        a4.setBids(Arrays.asList(b2,b3,b1));
        a5.setBids(Arrays.asList(b3,b1,b2));
        a6.setBids(Arrays.asList(b3,b2,b1));
        
        
        assertEquals(b3.getBidder(), a1.getLatestBidder());
        assertEquals(b3.getBidder(), a2.getLatestBidder());
        assertEquals(b3.getBidder(), a3.getLatestBidder());
        assertEquals(b3.getBidder(), a4.getLatestBidder());
        assertEquals(b3.getBidder(), a5.getLatestBidder());
        assertEquals(b3.getBidder(), a6.getLatestBidder());
    }
    @Test
    public void isBidsOverReservedPriceTest() {
        Auction a1 = new Auction();
        Auction a2 = new Auction();
        Auction a3 = new Auction();
        Auction a4 = new Auction();
        for (Auction auction : Arrays.asList(a1,a2,a3,a4)) {
            auction.setReservationPrice(1000);
        }
        
        Bid b1 = new Bid();
        b1.setAmount(1);
        Bid b500_1 = new Bid();
        b500_1.setAmount(500);
        Bid b500_2 = new Bid();
        b500_2.setAmount(500);
        Bid b1000 = new Bid();
        b1000.setAmount(1000);
        Bid b1001 = new Bid();
        b1001.setAmount(1001);
        
        a1.setBids(Arrays.asList(b1)); // Lower than reservedPrice
        a2.setBids(Arrays.asList(b1, b500_1, b500_2)); // Each lower than reservedPrice, summed is over.
        a3.setBids(Arrays.asList(b1000)); // Same as reservedPrice
        a4.setBids(Arrays.asList(b1001)); // Higher than reservedPrice
        
        assertFalse(a1.isReservedPriceMet());
        assertFalse(a2.isReservedPriceMet());
        assertTrue(a3.isReservedPriceMet());
        assertTrue(a4.isReservedPriceMet());
    }
    @Test
    public void setItemTest() {
        a.setId(1L);
        Car item = new Car();
        item.setId(2L);
        
        a.setItem(item);
        
        assertEquals(item, a.getItem());
        assertEquals(a, item.getAuction());
    }
    
    // NullTests
    
    @Test(expected = NullPointerException.class)
    public void addNullBidTest() {
        a.addBid(null);
    }
    @Test
    public void addBidToUnsetListTest() {
        a.addBid(new Bid());
        
        assertFalse(a.getBids().isEmpty());
    }
    @Test
    public void bidsIsNeverNullTest() {
        a.setBids(null);
        
        assertNotNull(a.getBids());
    }
    @Test
    public void getHighestBidFromNulledListTest() {
        a.setBids(null);
        
        int bid = a.getHighestBid();
        
        assertEquals(0, bid);
    }
    @Test
    public void getIdCouldBeNullTest() {
        Long id = a.getId();
        
        assertNull(id);
    }
    @Test
    public void getItemCanBeNullTest() {
        assertNull(a.getItem());
    }
    @Test
    public void getLatestBidFromNulledListTest() {
        a.setBids(null);
        
        assertNull(a.getLatestBid());
    }
    @Test
    public void getLatestBidderFromNulledListTest() {
        a.setBids(null);
        
        assertNull(a.getLatestBidder());
    }
    @Test
    public void getTimeOfEndNullTest() {
        assertNull(a.getTimeOfEnd());
    }
    @Test
    public void isBidsOverReservedPriceOnNulledListTest() {
        a.setBids(null);
        
        assertFalse(a.isReservedPriceMet());
    }
    @Test
    public void removeBidTest() {
        Bid b = new Bid();
        b.setId(2L);
        a.addBid(b);
        a.removeBid(b);
        
        assertEquals(0, a.getBids().size());
    }
    @Test
    public void removeBidFromNulledListTest() {
        a.setBids(null);
        a.removeBid(new Bid());
    }
    @Test(expected = IllegalArgumentException.class)
    public void setIdToNullTest() {
        a.setId(null);
    }
    @Test(expected = NullPointerException.class)
    public void setItemToNullTest() {
        a.setItem(null);
    }
}
