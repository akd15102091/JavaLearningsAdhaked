package DecoratorInterface;

import ComponentInterface.Pizza;

public abstract class PizzaDecorator implements Pizza{
    protected Pizza decoratedPizza ;

    public PizzaDecorator(Pizza decoratedPizza){
        this.decoratedPizza = decoratedPizza;
    }

    @Override
    public String getDescription(){
        return this.decoratedPizza.getDescription();
    }

    @Override
    public double getCost(){
        return this.decoratedPizza.getCost() ;
    }
} 