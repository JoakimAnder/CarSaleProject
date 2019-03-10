package dao;

import Entities.Auction;
import Entities.Bidder;
import Entities.Car;
import Entities.Condition;
import java.time.LocalDateTime;
import java.util.List;

public interface DaoFacade {
    void create(Object o);
    <T> T get(Class<T> oClass, Long id);
    void update(Object o);
    void delete(Object o);
    <T> List<T> getAll(Class<T> oClass);
    
    List<Bidder> getBidderByName(String name);
    
    List<Car> getCarByCondition(Condition condition);
    List<Car> getCarByModel(String model);
    List<Car> getCarByManufacturer(String manufacturer);
    List<Car> getCarByManufactureYear(int min, int max);
    List<Car> getCarByHasAuction(boolean hasAuction);
    
    
    List<Auction> getAuctionBySold(boolean isSold);
    List<Auction> getAuctionByEndTime(LocalDateTime min, LocalDateTime max);
    List<Auction> getAuctionByHighestBid(int min, int max, boolean sold);
}
