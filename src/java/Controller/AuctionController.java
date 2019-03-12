package Controller;

import Entities.Auction;
import Entities.Bid;
import Entities.Bidder;
import Entities.Car;
import REST.AuctionWrapper;
import REST.BidderWrapper;
import dao.DaoFacade;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuctionController {

    @Inject
    private DaoFacade dao;

    public void setDao(DaoFacade dao) {
        this.dao = dao;
    }

    public Auction unwrap(AuctionWrapper aw) {
        Auction a = new Auction();
        a.setId(aw.getId());
        a.setReservationPrice(aw.getReservationPrice());
        a.setValuedPrice(aw.getValuedPrice());
        a.setTimeOfEnd(aw.getTimeOfEnd());
        if (aw.getItem() != null) {
            a.setItem(dao.get(Car.class, aw.getItem().getId()));
        }
        if (aw.getBids() != null) {
            a.setBids(aw.getBids().stream()
                    .map(b -> dao.get(Bid.class, b.getId()))
                    .collect(Collectors.toList()));
        }

        return a;
    }

    public Bidder unwrap(BidderWrapper bw) {
        Bidder b = new Bidder();
        b.setId(bw.getId());
        b.setName(bw.getName());
        b.setPhone(bw.getPhone());
        b.setAddress(bw.getAddress());
        if (bw.getBids() != null) {
            b.setBids(bw.getBids().stream()
                    .map(bid -> dao.get(Bid.class, bid.getId()))
                    .collect(Collectors.toList()));
        }

        return b;
    }

    public void add(Auction auction) {
        dao.create(auction);
    }

    public List<Auction> getAllAvaliable() {
        return dao.getAuctionBySold(false);
    }

    public List<Auction> getAllSold() {
        return dao.getAuctionBySold(true);
    }

    public List<Auction> getByHighestBid(int min, int max, boolean sold) {
        return dao.getAuctionByHighestBid(min, max, sold);
    }
    
    public List<Auction> getByEndTime(LocalDateTime min, LocalDateTime max) {
        if (min.isAfter(max)) {
            LocalDateTime temp = min;
            min = max;
            max = temp;
        }
        return dao.getAuctionByEndTime(min, max);
    }

    public boolean bid(Auction auction, Bidder bidder, int bid) {
        if (auction.getTimeOfEnd().isBefore(LocalDateTime.now()))
            return false;
        if (auction.getHighestBid() >= bid) {
            return false;
        }
        Bidder latestBidder = auction.getLatestBidder();
        if (latestBidder != null) {
            if (latestBidder.equals(bidder)) {
                return false;
            }
        }

        Bid b = new Bid(bid);

        dao.get(Auction.class, auction.getId()).addBid(b);
        dao.get(Bidder.class, bidder.getId()).addBid(b);

        dao.create(b);
        return true;
    }

    public Auction get(Long id) {
        return dao.get(Auction.class, id);
    }

    public Bid getBid(Long id) {
        return dao.get(Bid.class, id);
    }

    public List<Auction> getAll() {
        return dao.getAll(Auction.class);
    }
}
