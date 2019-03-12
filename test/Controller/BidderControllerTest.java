package Controller;

import Entities.Bid;
import Entities.Bidder;
import REST.BidderWrapper;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BidderControllerTest {
    
    private BidderController bc;
    private MockDao mockDao;
    private Bidder b;
    
    public BidderControllerTest() {
    }
    
    @Before
    public void setup() {
        bc = new BidderController();
        mockDao = new MockDao();
        
        
        bc.setDao(mockDao);
        b = new Bidder();
        b.setId(1L);
    }

    @Test
    public void addBidderTest() {
        List<Bidder> list = mockDao.getBidders();
        
        bc.add(b);
        
        assertEquals("create(Bidder)", mockDao.getCalledMethod());
        assertEquals(1, list.size());
        assertEquals(b, list.get(0));
    }

    @Test
    public void getBidderTest() {
        mockDao.getBidders().add(b);
        
        Bidder result = bc.get(1L);
        
        assertEquals("get(Bidder,1)", mockDao.getCalledMethod());
        assertEquals(b, result);
    }

    @Test
    public void getBiddersByNameTest() {
        String name = "testName";
        
        bc.get(name);
        
        assertEquals("getBidderByName("+name+")", mockDao.getCalledMethod());
    }

    @Test
    public void getAllTest() {
        bc.getAll();
        
        assertEquals("getAll(Bidder)", mockDao.getCalledMethod());
    }

    @Test
    public void unwrapTest() {
        Bid bid = new Bid();
        bid.setId(2L);
        mockDao.bidList.add(bid);
        Long id = 1L;
        String address = "testAdress";
        String name = "testName";
        String phone = "testPhone";
        b.setId(id);
        b.setAddress(address);
        b.setPhone(phone);
        b.setName(name);
        b.addBid(bid);
        BidderWrapper bw = BidderWrapper.wrap(b);
        
        Bidder result = bc.unwrap(bw);
        
        assertEquals(b, result);
        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
        assertEquals(address, result.getAddress());
        assertEquals(id, result.getId());
        assertEquals(1, result.getBids().size());
        assertEquals(bid, result.getBids().get(0));
    }
    
}
