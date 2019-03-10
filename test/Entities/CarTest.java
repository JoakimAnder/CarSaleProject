package Entities;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class CarTest {

    Car c;
    
    public CarTest() {
    }
    
    @Before
    public void setup() {
        c = new Car();
    }
    
    @Test
    public void addingCarToAuctionTest() {
        Auction a = new Auction();
        a.setId(2L);
        
        a.setItem(c);
        
        assertEquals(a.getId(), c.getAuction().getId());
    }
    
    @Test
    public void calcValue() {
        int newPrice = 200000;
        int elapsedYears = 5;
        int manufactureYear = LocalDate.now().getYear() - elapsedYears;
        
        c.setCurrentCondition(Condition.USED);
        c.setManufactureYear(manufactureYear);
        c.setNewPrice(newPrice);
        
        Double value = c.value();
        Double expectedValue = (newPrice*0.9) * Math.pow(0.8, elapsedYears) - 10000;
        
        assertEquals(expectedValue, value);
        
    }

}
