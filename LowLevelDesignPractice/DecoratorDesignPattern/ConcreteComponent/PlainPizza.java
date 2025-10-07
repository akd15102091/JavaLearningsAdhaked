package ConcreteComponent;

import ComponentInterface.Pizza;

public class PlainPizza implements Pizza{
    String description = "Plain pizza";
    double cost = 100 ;
    public String getDescription(){
        return this.description ;
    }
    public double getCost(){
        return this.cost ;
    }
}
