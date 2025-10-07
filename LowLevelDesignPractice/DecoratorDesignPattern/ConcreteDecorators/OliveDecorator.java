package ConcreteDecorators;

import ComponentInterface.Pizza;
import DecoratorInterface.PizzaDecorator;

public class OliveDecorator extends PizzaDecorator{

    public OliveDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription(){
        return this.decoratedPizza.getDescription()+", Olive";
    }

    @Override
    public double getCost(){
        return this.decoratedPizza.getCost() + 70 ;
    }
    
}
