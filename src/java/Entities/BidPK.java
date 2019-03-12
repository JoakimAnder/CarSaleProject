package Entities;

import java.io.Serializable;
import java.util.Objects;

public class BidPK implements Serializable {
    
    
    private Long id;
    private Long auction;
    private Long bidder;
    
    public BidPK(){}

    public BidPK(Long auction, Long bidder, Long id) {
        this.auction = auction;
        this.bidder = bidder;
        this.id = id;
    }

    
    
    
    // Getters and Setters

    public Long getAuction() {
        return auction;
    }

    public void setAuction(Long auction) {
        this.auction = auction;
    }

    public Long getBidder() {
        return bidder;
    }

    public void setBidder(Long bidder) {
        this.bidder = bidder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BidPK other = (BidPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    
    
    
    
}
