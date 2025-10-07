package ConcreteDecorators;

import ComponentInterface.Pizza;
import DecoratorInterface.PizzaDecorator;

public class PepperoniDecorator extends PizzaDecorator{

    public PepperoniDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription(){
        return this.decoratedPizza.getDescription()+", Pepperoni";
    }

    @Override
    public double getCost(){
        return this.decoratedPizza.getCost() + 40 ;
    }
    
}
