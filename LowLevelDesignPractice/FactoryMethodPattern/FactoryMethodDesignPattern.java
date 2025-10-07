/*
 * Let’s understand factory method design pattern using an example. Below is the problem statement to understand it:

    You are developing a software system for an e-commerce platform that deals with various types of products. 
    Each product category (e.g., electronics, clothing, books) requires specific handling during creation. 
    However, you want to decouple the client code from the concrete product creation logic to enhance flexibility and maintainability. Additionally, you want to allow for easy extension by adding new product types in the future without modifying existing code.
 */


// Product Interface
interface Product {
    void display();
}

// Concrete Products
class ConcreteProductA implements Product {
    @Override
    public void display() {
        System.out.println("This is Concrete Product A.");
    }
}

class ConcreteProductB implements Product {
    @Override
    public void display() {
        System.out.println("This is Concrete Product B.");
    }
}

// Factory Interface
interface Factory {
    Product factoryMethod();
}

// Concrete Factories
class ConcreteFactoryA implements Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProductA();
    }
}

class ConcreteFactoryB implements Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProductB();
    }
}

// Client Code
public class FactoryMethodDesignPattern {
    public static void main(String[] args) {
        Factory factoryA = new ConcreteFactoryA();
        Product productA = factoryA.factoryMethod();
        productA.display();

        Factory factoryB = new ConcreteFactoryB();
        Product productB = factoryB.factoryMethod();
        productB.display();
    }
}
