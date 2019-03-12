package Main;

import Controller.WebCarController;
import Entities.Car;
import Entities.Condition;

public class NewMain {

    public static void main(String[] args) {
        WebCarController cc = new WebCarController();
        
        cc.add(new Car("f4", 50000, "Volvo", 2008, Condition.MINT));
        System.out.println(cc.getAll());
        
    }
}