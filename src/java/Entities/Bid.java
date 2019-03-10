package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@IdClass(BidPK.class)
public class Bid implements Serializable {
    
    private int amount;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timeOfBid;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Id
    @ManyToOne
    private Auction auction;
    
    @Id
    @ManyToOne
    private Bidder bidder;

    public Bid() {
    }

    public Bid(int amount) {
        this.amount = amount;
        timeOfBid = LocalDateTime.now();
    }

    
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeOfBid() {
        return timeOfBid;
    }

    public void setTimeOfBid(LocalDateTime timeOfBid) {
        this.timeOfBid = timeOfBid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        if (auction == null)
            this.auction = null;
        if (this.auction != null)
            if (!this.auction.equals(auction))
                throw new IllegalArgumentException("A bid cannot change auction");
        this.auction = auction;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        if (bidder == null)
            this.bidder = null;
        if (this.bidder != null)
            if (!this.bidder.equals(bidder))
                throw new IllegalArgumentException("A bid cannot change bidder");
        this.bidder = bidder;
    }
    
    // Misc

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Bid other = (Bid) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bid{" + "amount=" + amount + ", timeOfBid=" + timeOfBid + ", id=" + id + '}';
    }
    
    
}
