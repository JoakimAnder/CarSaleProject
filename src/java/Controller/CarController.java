package Controller;

import Entities.Car;
import Entities.Condition;
import dao.DaoFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CarController implements Serializable {
    
    public CarController() {}
    
    @Inject
    DaoFacade dao;
    
    public void add(Car car) {
        dao.create(car);
    }
    public List<Car> getCars() {
        return dao.getAll(Car.class);
    }
    public List<Car> getByHasAuction(boolean hasAuction) {
        return dao.getCarByHasAuction(hasAuction);
    }
    public List<Car> getByManufactureYear(int min, int max) {
        return dao.getCarByManufactureYear(min, max);
    }
    public List<Car> getByManufacturer(String manufacturer) {
        return dao.getCarByManufacturer(manufacturer);
    }
    public List<Car> getByModel(String model) {
        return dao.getCarByModel(model);
    }
    public List<Car> getByCondition(Condition condition) {
        return dao.getCarByCondition(condition);
    }
}
