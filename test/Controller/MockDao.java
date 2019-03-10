package Controller;

import Entities.Auction;
import Entities.Bid;
import Entities.Bidder;
import Entities.Car;
import Entities.Condition;
import dao.DaoFacade;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class MockDao implements DaoFacade {

    private String methodCalled;
    
    List<Bidder> bidderList = new ArrayList<>();
    List<Auction> auctionList = new ArrayList<>();
    List<Bid> bidList = new ArrayList<>();
    List<Car> carList = new ArrayList<>();

    public MockDao() {
    }

    public String getCalledMethod() {
        return methodCalled;
    }
    public List<Auction> getAuctions() {
        return auctionList;
    }
    public List<Bidder> getBidders() {
        return bidderList;
    }
    public List<Bid> getBids() {
        return bidList;
    }
    public List<Car> getCars() {
        return carList;
    }


    @Override
    public void create(Object o) {
        methodCalled = "create(" + o.getClass().getSimpleName() + ")";

        switch(o.getClass().getSimpleName()) {
            case "Auction":
                auctionList.add((Auction) o);
                break;
            case "Bidder":
                bidderList.add((Bidder) o);
                break;
            case "Bid":
                bidList.add((Bid) o);
                break;
            case "Car":
                carList.add((Car) o);
                break;
            default:
                throw new IllegalArgumentException("Class " + o.getClass() + " should not be handled in this Controller");
        }
    }

    @Override
    public <T> T get(Class<T> oClass, Long id) {
        methodCalled = "get(" + oClass.getSimpleName() + "," + id + ")";
        
        if (oClass.equals(Auction.class)) {
            for (Auction a : auctionList) {
                if (a.getId().equals(id)) {
                    return (T) a;
                }
            }
        } else if (oClass.equals(Bidder.class)) {
            for (Bidder b : bidderList) {
                if (b.getId().equals(id)) {
                    return (T) b;
                }
            }
        } else if (oClass.equals(Bid.class)) {
            for (Bid b : bidList) {
                if (b.getId().equals(id)) {
                    return (T) b;
                }
            }
        } else if (oClass.equals(Car.class)) {
            for (Car c : carList) {
                if (c.getId().equals(id)) {
                    return (T) c;
                }
            }
        } else {
            throw new IllegalArgumentException("Class " + oClass + " should not be handled in this Controller");
        }

        return null;
    }

    @Override
    public void update(Object o) {
        methodCalled = "update(" + o.getClass().getSimpleName() + ")";
    }

    @Override
    public void delete(Object o) {
        methodCalled = "delete(" + o.getClass().getSimpleName() + ")";
    }

    @Override
    public <T> List<T> getAll(Class<T> oClass) {
        methodCalled = "getAll(" + oClass.getSimpleName() + ")";

        if (oClass.equals(Auction.class)) {
            return (List<T>) auctionList;
        }else if (oClass.equals(Bidder.class)) {
            return (List<T>) bidderList;
        }else if (oClass.equals(Bid.class)) {
            return (List<T>) bidList;
        }else if (oClass.equals(Car.class)) {
            return (List<T>) carList;
        } else {
            throw new IllegalArgumentException("Class " + oClass + " should not be handled in this Controller");
        }
    }

    @Override
    public List<Bidder> getBidderByName(String name) {
        methodCalled = "getBidderByName("+name+")";
        return new ArrayList<>();
    }

    @Override
    public List<Car> getCarByCondition(Condition condition) {
        methodCalled = "getCarByCondition("+condition+")";
        return new ArrayList<>();
    }

    @Override
    public List<Car> getCarByModel(String model) {
        methodCalled = "getCarByModel("+model+")";
        return new ArrayList<>();
    }

    @Override
    public List<Car> getCarByManufacturer(String manufacturer) {
        methodCalled = "getCarByManufacturer("+manufacturer+")";
        return new ArrayList<>();
    }

    @Override
    public List<Car> getCarByManufactureYear(int min, int max) {
        methodCalled = "getCarByManufactureYear("+min+","+max+")";
        return new ArrayList<>();
    }

    @Override
    public List<Car> getCarByHasAuction(boolean hasAuction) {
        methodCalled = "getCarByHasAuction("+hasAuction+")";
        return new ArrayList<>();
    }

    @Override
    public List<Auction> getAuctionBySold(boolean isSold) {
        methodCalled = "getAuctionBySold("+isSold+")";
        return new ArrayList<>();
    }

    @Override
    public List<Auction> getAuctionByEndTime(LocalDateTime min, LocalDateTime max) {
        methodCalled = "getAuctionByEndTime("+min+","+max+")";
        return new ArrayList<>();
    }

    @Override
    public List<Auction> getAuctionByHighestBid(int min, int max, boolean sold) {
        methodCalled = "getAuctionByHighestBet("+min+","+max+","+sold+")";
        return new ArrayList<>();
    }
    
}
