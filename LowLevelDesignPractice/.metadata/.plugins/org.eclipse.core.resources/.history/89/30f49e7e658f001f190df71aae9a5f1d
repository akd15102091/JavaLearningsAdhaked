package ConcreteDecorators;

import ComponentInterface.Pizza;
import DecoratorInterface.PizzaDecorator;

public class CheeseDecorator extends PizzaDecorator{

    public CheeseDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription(){
        return this.decoratedPizza.getDescription()+", Cheese";
    }

    @Override
    public double getCost(){
        return this.decoratedPizza.getCost() + 50 ;
    }
    
}
