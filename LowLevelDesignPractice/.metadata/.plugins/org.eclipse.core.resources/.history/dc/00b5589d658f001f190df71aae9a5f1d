//Client Code

import ComponentInterface.Pizza;
import ConcreteComponent.PlainPizza;
import ConcreteDecorators.CheeseDecorator;
import ConcreteDecorators.MushroomDecorator;
import ConcreteDecorators.OliveDecorator;
import ConcreteDecorators.PepperoniDecorator;

public class PizzaShop {
    public static void main(String[] args) {
        Pizza pizza = new PlainPizza() ;
        System.out.println("Desc : "+pizza.getDescription() +" AND Cost : "+pizza.getCost());

        pizza = new CheeseDecorator(pizza) ;
        System.out.println("Desc : "+pizza.getDescription() +" AND Cost : "+pizza.getCost());

        pizza = new MushroomDecorator(pizza);
        System.out.println("Desc : "+pizza.getDescription() +" AND Cost : "+pizza.getCost());

        pizza = new OliveDecorator(pizza) ;
        System.out.println("Desc : "+pizza.getDescription() +" AND Cost : "+pizza.getCost());

        pizza = new PepperoniDecorator(pizza) ;
        System.out.println("Desc : "+pizza.getDescription() +" AND Cost : "+pizza.getCost());
    }
}
