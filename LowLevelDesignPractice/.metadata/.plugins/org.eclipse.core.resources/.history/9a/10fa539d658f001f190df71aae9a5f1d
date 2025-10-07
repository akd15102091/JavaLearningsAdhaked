package ConcreteDecorators;

import ComponentInterface.Pizza;
import DecoratorInterface.PizzaDecorator;

public class MushroomDecorator extends PizzaDecorator{

    public MushroomDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription(){
        return this.decoratedPizza.getDescription()+", Mushroom";
    }

    @Override
    public double getCost(){
        return this.decoratedPizza.getCost() + 60 ;
    }
    
}
