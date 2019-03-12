package REST;

import Entities.Auction;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuctionWrapper implements Serializable {
    
    private Long id;
    private LocalDateTime timeOfEnd;
    private int reservationPrice;
    private int valuedPrice;
    private CarWrapper item;
    private List<BidWrapper> bids;

    public AuctionWrapper() {
    }

    public static AuctionWrapper wrap(Auction a) {
        return (a == null ? null : new AuctionWrapper(a));
    }
    public static List<AuctionWrapper> wrap(List<Auction> aList) {
        return (aList == null) ? 
                null : 
                aList.stream().map(e -> new AuctionWrapper(e)).collect(Collectors.toList());
    }
    
    private AuctionWrapper(Auction a) {
        this.id = a.getId();
        this.timeOfEnd = a.getTimeOfEnd();
        this.reservationPrice = a.getReservationPrice();
        this.valuedPrice = a.getValuedPrice();
        this.item = CarWrapper.wrap(a.getItem());
        if(a.getBids() != null)
            this.bids = BidWrapper.wrap(a.getBids());
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfEnd() {
        return timeOfEnd;
    }

    public void setTimeOfEnd(LocalDateTime timeOfEnd) {
        this.timeOfEnd = timeOfEnd;
    }

    public int getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(int reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public int getValuedPrice() {
        return valuedPrice;
    }

    public void setValuedPrice(int valuedPrice) {
        this.valuedPrice = valuedPrice;
    }

    public CarWrapper getItem() {
        return item;
    }

    public void setItem(CarWrapper item) {
        this.item = item;
    }


    public List<BidWrapper> getBids() {
        return bids;
    }

    public void setBids(List<BidWrapper> bids) {
        this.bids = bids;
    }
    
}
