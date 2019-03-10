package Controller;

import Entities.Auction;
import Entities.Bid;
import Entities.Bidder;
import Entities.Car;
import REST.AuctionWrapper;
import REST.BidderWrapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AuctionControllerTest {

    public AuctionControllerTest() {
    }

    AuctionController ac;
    MockDao mockDao;

    @Before
    public void setup() {
        mockDao = new MockDao();
        ac = new AuctionController();
        ac.setDao(mockDao);
    }

    @Test
    public void addAuctionTest() {
        ac.add(new Auction());

        assertEquals("create(Auction)", mockDao.getCalledMethod());
    }

    @Test
    public void getAuctionTest() {
        ac.get(1L);

        assertEquals("get(Auction,1)", mockDao.getCalledMethod());
    }

    @Test
    public void getAllTest() {
        ac.getAll();

        assertEquals("getAll(Auction)", mockDao.getCalledMethod());
    }

    @Test
    public void getAllAvaliableTest() {
        ac.getAllAvaliable();

        assertEquals("getAuctionBySold(false)", mockDao.getCalledMethod());
    }

    @Test
    public void getAllSoldTest() {
        ac.getAllSold();

        assertEquals("getAuctionBySold(true)", mockDao.getCalledMethod());
    }

    @Test
    public void getByEndTimeTest() {
        LocalDateTime min = LocalDateTime.now().minusDays(5L);
        LocalDateTime max = LocalDateTime.now().plusDays(5L);

        ac.getByEndTime(min, max);

        assertEquals("getAuctionByEndTime(" + min + "," + max + ")", mockDao.getCalledMethod());
    }

    @Test
    public void getByEndTimeReversedTest() {
        LocalDateTime min = LocalDateTime.now().minusDays(5L);
        LocalDateTime max = LocalDateTime.now().plusDays(5L);

        ac.getByEndTime(max, min);

        assertEquals("getAuctionByEndTime(" + min + "," + max + ")", mockDao.getCalledMethod());
    }

    @Test
    public void unwrapAuctionTest() {
        Bid[] bids = {new Bid(), new Bid()};
        bids[0].setId(11L);
        bids[1].setId(12L);
        mockDao.bidList.addAll(Arrays.asList(bids));
        
        Car c = new Car();
        c.setId(13L);
        
        mockDao.carList.add(c);

        Auction a1 = new Auction();
        a1.setBids(Arrays.asList(bids));
        a1.setId(1L);
        a1.setItem(c);
        a1.setReservationPrice(1);
        a1.setTimeOfEnd(LocalDateTime.now());

        AuctionWrapper aw = AuctionWrapper.wrap(a1);
        c.setAuction(null);

        Auction a2 = ac.unwrap(aw);

        assertEquals(a1, a2);
        assertEquals(a1.getId(), a2.getId());
        assertEquals(a1.getReservationPrice(), a2.getReservationPrice());
        assertEquals(a1.getValuedPrice(), a2.getValuedPrice());
        assertEquals(a1.getTimeOfEnd(), a2.getTimeOfEnd());
        assertEquals(a1.getItem(), a2.getItem());
        assertEquals(a1.getBids().size(), a2.getBids().size());
        assertEquals(a1.getBids(), a2.getBids());

    }

    @Test
    public void unwrapBidderTest() {
        Bid[] bids = {new Bid(), new Bid()};
        bids[0].setId(11L);
        bids[1].setId(12L);
        mockDao.bidList.addAll(Arrays.asList(bids));

        Bidder b1 = new Bidder();
        b1.setBids(Arrays.asList(bids));
        b1.setId(1L);
        b1.setAddress("testAddress");
        b1.setName("testName");
        b1.setPhone("testPhone");

        Bidder b2 = ac.unwrap(BidderWrapper.wrap(b1));

        assertEquals(b1, b2);
        assertEquals(b1.getId(), b2.getId());
        assertEquals(b1.getName(), b2.getName());
        assertEquals(b1.getPhone(), b2.getPhone());
        assertEquals(b1.getAddress(), b2.getAddress());
        assertEquals(b1.getBids().size(), b2.getBids().size());
        assertEquals(b1.getBids(), b2.getBids());
    }

    @Test
    public void legalBidTest() {
        Auction a = new Auction();
        a.setId(1L);
        a.setTimeOfEnd(LocalDateTime.MAX);

        Bidder b = new Bidder();
        b.setId(2L);
        mockDao.bidderList.add(b);
        
        ac.add(a);

        int amount = 1;
        LocalDateTime now = LocalDateTime.now();

        boolean bidAdded = ac.bid(a, b, amount);

        assertTrue(bidAdded);

        Bid bid = mockDao.bidList.get(0);
        assertEquals(a, bid.getAuction());
        assertEquals(b, bid.getBidder());

        assertTrue(bid.getTimeOfBid().isAfter(now));
        assertTrue(bid.getTimeOfBid().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertEquals(amount, bid.getAmount());
    }

    @Test
    public void tooLateBidTest() {
        Auction a = new Auction();
        a.setId(1L);
        a.setTimeOfEnd(LocalDateTime.MIN);

        Bidder b = new Bidder();
        b.setId(2L);
        mockDao.bidderList.add(b);
        ac.add(a);

        int amount = 1;

        boolean bidAdded = ac.bid(a, b, amount);

        assertFalse(bidAdded);
    }

    @Test
    public void tooLowBidTest() {
        Auction a = new Auction();
        a.setId(1L);
        a.setTimeOfEnd(LocalDateTime.MIN);

        Bidder b = new Bidder();
        b.setId(2L);
        mockDao.bidderList.add(b);
        ac.add(a);

        int amount = -1;

        boolean bidAdded = ac.bid(a, b, amount);

        assertFalse(bidAdded);
    }

    @Test
    public void sameBidderBidTest() {
        Auction a = new Auction();
        a.setId(1L);
        a.setTimeOfEnd(LocalDateTime.MIN);

        Bidder b = new Bidder();
        b.setId(2L);
        mockDao.bidderList.add(b);
        ac.add(a);

        int amount = 1;

        ac.bid(a, b, amount);
        boolean bidAdded = ac.bid(a, b, amount + 1);

        assertFalse(bidAdded);
    }

    @Test
    public void severalBidsTest() {
        //TODO
    }

}