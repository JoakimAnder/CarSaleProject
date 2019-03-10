package REST;

import Controller.CarController;
import Entities.Car;
import Entities.Condition;
import static REST.CarWrapper.wrap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/car")
public class CarRest {
    
    @Inject
    CarController cc;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCar(Car car) {
        cc.add(car);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCars() {
        return wrap(cc.getCars());
    }
    
    @GET
    @Path("/claimed")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsWithAuctions() {
        return wrap(cc.getByHasAuction(true));
    }
    
    @GET
    @Path("/unclaimed")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsWithoutAuctions() {
        return wrap(cc.getByHasAuction(false));
    }
    
    @GET
    @Path("/year/{min}/{max}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsByManufactureYear(@PathParam("min") int min, @PathParam("max") int max) {
        return wrap(cc.getByManufactureYear(min, max));
    }
    
    @GET
    @Path("/manufacturer/{manu}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsByManufacturer(@PathParam("manu") String manufacturer) {
        return wrap(cc.getByManufacturer(manufacturer));
    }
    
    @GET
    @Path("/{model}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsByModel(@PathParam("model") String model) {
        return wrap(cc.getByModel(model));
    }
    
    @GET
    @Path("/condition/{condition}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWrapper> getCarsByCondition(@PathParam("condition") String condition) {
        return wrap(cc.getByCondition(Condition.valueOf(condition)));
    }
}
