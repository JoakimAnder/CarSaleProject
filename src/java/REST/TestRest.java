package REST;

import Controller.AuctionController;
import Controller.BidderController;
import Controller.CarController;
import Entities.Auction;
import Entities.Bid;
import Entities.Bidder;
import Entities.Car;
import Entities.Condition;
import dao.DaoFacade;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/test")
public class TestRest {
    
    @Inject
    DaoFacade dao;
    
    @Inject
    AuctionController ac;
    
    @Inject
    BidderController bc;
    
    @Inject
    CarController cc;
    
    @GET
    @Path("/ac/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuctionWrapper> getA() {
        AuctionWrapper a = AuctionWrapper.wrap(ac.get(1L));
        List<AuctionWrapper> a1 = AuctionWrapper.wrap(ac.getAll());
        List<AuctionWrapper> a2 = AuctionWrapper.wrap(ac.getAllAvaliable());
        List<AuctionWrapper> a3 = AuctionWrapper.wrap(ac.getAllSold());
        List<AuctionWrapper> a4 = AuctionWrapper.wrap(ac.getByEndTime(LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(2)));
        List<AuctionWrapper> a5 = AuctionWrapper.wrap(ac.getByHighestBid(2, 200, true));
        List<AuctionWrapper> a6 = AuctionWrapper.wrap(ac.getByHighestBid(2, 200, false));
        
        return a6;
    }
    @GET
    @Path("/bc/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BidderWrapper> getB() {
        bc.get(1L);
        return BidderWrapper.wrap(bc.get("testName"));
    }
    @GET
    @Path("/cc/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getC() {
        List<CarWrapper> c1 = CarWrapper.wrap(cc.getByCondition(Condition.MINT));
        List<CarWrapper> c2 = CarWrapper.wrap(cc.getByHasAuction(true));
        List<CarWrapper> c3 = CarWrapper.wrap(cc.getByHasAuction(false));
        List<CarWrapper> c4 = CarWrapper.wrap(cc.getByManufactureYear(1000, 2000));
        List<CarWrapper> c5 = CarWrapper.wrap(cc.getByManufacturer("Volvo"));
        List<CarWrapper> c6 = CarWrapper.wrap(cc.getByModel("C123"));
        List<CarWrapper> c7 = CarWrapper.wrap(cc.getCars());
        
        return c7;
    }
    
    @GET
    @Path("/dao/get")
    public Object daoGet1Test() {
        AuctionWrapper a = AuctionWrapper.wrap(dao.get(Auction.class, 1L));
        BidderWrapper b = BidderWrapper.wrap(dao.get(Bidder.class, 1L));
        CarWrapper c = CarWrapper.wrap(dao.get(Car.class, 1L));
//        dao.get(Bid.class, 1L);
        return a;
    }
    
    @GET
    @Path("/dao/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> daoGetAllTest() {
        List<AuctionWrapper> a = AuctionWrapper.wrap(dao.getAll(Auction.class));
        List<BidderWrapper> b1 = BidderWrapper.wrap(dao.getAll(Bidder.class));
        List<BidWrapper> b2 = BidWrapper.wrap(dao.getAll(Bid.class));
        List<CarWrapper> c = CarWrapper.wrap(dao.getAll(Car.class));
        
        return c;
    }
    @GET
    @Path("/dao/auction")
    public void daoGetAuctionsTest() {
        AuctionWrapper.wrap(dao.getAuctionByEndTime(LocalDateTime.now().minusMonths(2), LocalDateTime.now().plusMonths(2)));
        AuctionWrapper.wrap(dao.getAuctionByHighestBid(0, 100, true));
        AuctionWrapper.wrap(dao.getAuctionByHighestBid(0, 100, false));
        AuctionWrapper.wrap(dao.getAuctionBySold(true));
        AuctionWrapper.wrap(dao.getAuctionBySold(false));
    }
    @GET
    @Path("/dao/bidder")
    public void daoGetBiddersTest() {
        BidderWrapper.wrap(dao.getBidderByName("test"));
    }
    @GET
    @Path("/dao/car")
    public void daoGetCarsTest() {
        CarWrapper.wrap(dao.getCarByCondition(Condition.MINT));
        CarWrapper.wrap(dao.getCarByHasAuction(true));
        CarWrapper.wrap(dao.getCarByHasAuction(false));
        CarWrapper.wrap(dao.getCarByManufactureYear(0, 3000));
        CarWrapper.wrap(dao.getCarByManufacturer("Volvo"));
        CarWrapper.wrap(dao.getCarByModel("c2"));
    }
    

    @GET
    @Path("/fill")
    public boolean fillDB() {
        Random rand = new Random();

        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'Z'};
        String[] carModel = new String[20];

        for (int i = 0; i < carModel.length; i++) {
            carModel[i] = "" + letters[rand.nextInt(letters.length)] + (rand.nextInt(990) + 10);
        }

        int carPriceMin = 9000;
        int carPriceMax = 30000;
        String[] carManu = {"Volvo", "BMW", "Toyota", "Honda", "Hundai", "Tesla", "SAAB", "Ferarri", "Ford", "Audi"};
        int carManuYearMin = 1990;
        int carManuYearMax = 2018;
        Condition[] carCondition = Condition.values();

        for (String model : carModel) {
            dao.create(new Car(
                    model,
                    rand.nextInt(carPriceMax - carPriceMin) + carPriceMin,
                    carManu[rand.nextInt(carManu.length)],
                    rand.nextInt(carManuYearMax - carManuYearMin) + carManuYearMin,
                    carCondition[rand.nextInt(carCondition.length)]));
        }

        for (String s : carModel) {
            dao.create(new Auction(LocalDateTime.now().plusDays(rand.nextInt(130) - 120), 0));
        }

        List<Auction> aList = dao.getAll(Auction.class);
        List<Car> cList = dao.getAll(Car.class);

        for (int i = 0; i < aList.size(); i++) {
            aList.get(i).setItem(cList.get(i));
            aList.get(i).setReservationPrice(cList.get(i).getNewPrice() * 3);
        }

        dao.create(new Car(
                "Test123",
                rand.nextInt(carPriceMax - carPriceMin) + carPriceMin,
                carManu[rand.nextInt(carManu.length)],
                rand.nextInt(carManuYearMax - carManuYearMin) + carManuYearMin,
                carCondition[rand.nextInt(carCondition.length)]));
        
        dao.create(new Bidder("testName", "testPhone", "testAddress"));
        
        
        List<Bidder> bl = dao.getAll(Bidder.class);
        List<Auction> al = dao.getAll(Auction.class);
        
        Bidder b = bl.get(rand.nextInt(bl.size()));
        Auction a = al.get(rand.nextInt(al.size()));
        
        Bid bid = new Bid(100);
        b.addBid(bid);
        a.addBid(bid);
        dao.create(bid);
        
        return true;
    }
}
