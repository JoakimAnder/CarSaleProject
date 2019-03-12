package Controller;

import dao.DaoFacade;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TestController {
    
    @Inject
    DaoFacade dao;
    
    
    
    public void create(Object o) {
        dao.create(o);
    }
    public <T> T get(Class<T> oClass, Long id) {
        return dao.get(oClass, id);
    }
    public void update(Object o) {
        dao.update(o);
    }
    public void delete(Object o) {
        
    }
//    public <T> List<T> getAll(Class<T> oClass) {
//        
//    }
//    public List<Bidder> getBidderByName(String name) {
//        
//    }
//    public List<Car> getCarByCondition(Condition size) {
//        
//    }
//    public List<Car> getCarByModel(String model) {
//        
//    }
//    public List<Car> getCarByManufacturer(String manufacturer) {
//        
//    }
//    public List<Car> getCarByManufactureYear(int min, int max) {
//        
//    }
//    public List<Car> getCarByHasAuction(boolean hasAuction) {
//        
//    }
//    public List<Auction> getAuctionByHighestBid(int min, int max, boolean sold) {
//        
//    }
//    public List<Auction> getAuctionBySold(boolean sold) {
//        
//    }
//    public List<Auction> getAuctionByEndTime(LocalDateTime min, LocalDateTime max) {
//        
//    }
}
