/*
 * Imagine you’re managing a global car manufacturing company
    You want to design a system to create cars with specific configurations for different regions, such as North America and Europe. 
    Each region may have unique requirements and regulations, and you want to ensure that cars produced for each region meet those standards.
 
    
=>  What can be the challenges while implementing this system?
    1) Different regions have different cars with different features, so designing this can be challenging.
    2) The other main challenge is to ensure consistency in the production of cars and their specifications within each region.
    3) There can be updation in having new cars in different regions so adapting the system to changes in regulations or introducing new features for a specific region becomes challenging.
    4) So, Modifications would need to be made in multiple places, increasing the chances of introducing bugs and making the system more prone to errors.
    
=>  How Abstracy Factory Pattern help to solve above challenges?
    Below is how abstract factory pattern help to solve the above challenges. After using this pattern:

    1) Different regions has their own factory to create cars for local needs.
    2) This helps to kees the design and features the same for vehicles in each region.
    3) You can change one region without affecting others (e.g., updating North America doesn’t impact Europe).
    4) To add a new region, just create a new factory, no need to change existing code.
    5) The pattern keeps car creation separate from how they are used.
 
    */





// Abstract Factory Interface
interface CarFactory {
    Car createCar();
    CarSpecification createSpecification();
}

// Concrete Factory for North America Cars
class NorthAmericaCarFactory implements CarFactory {
    public Car createCar() {
        return new Sedan();
    }

    public CarSpecification createSpecification() {
        return new NorthAmericaSpecification();
    }
}

// Concrete Factory for Europe Cars
class EuropeCarFactory implements CarFactory {
    public Car createCar() {
        return new Hatchback();
    }

    public CarSpecification createSpecification() {
        return new EuropeSpecification();
    }
}

// Abstract Product Interface for Cars
interface Car {
    void assemble();
}

// Abstract Product Interface for Car Specifications
interface CarSpecification {
    void display();
}

// Concrete Product for Sedan Car
class Sedan implements Car {
    public void assemble() {
        System.out.println("Assembling Sedan car.");
    }
}

// Concrete Product for Hatchback Car
class Hatchback implements Car {
    public void assemble() {
        System.out.println("Assembling Hatchback car.");
    }
}

// Concrete Product for North America Car Specification
class NorthAmericaSpecification implements CarSpecification {
    public void display() {
        System.out.println("North America Car Specification: Safety features compliant with local regulations.");
    }
}

// Concrete Product for Europe Car Specification
class EuropeSpecification implements CarSpecification {
    public void display() {
        System.out.println("Europe Car Specification: Fuel efficiency and emissions compliant with EU standards.");
    }
}


// Client Code
public class AbstractFactoryPatternCarFactoryClient {
    public static void main(String[] args) {
        // Creating cars for North America
        CarFactory northAmericaFactory = new NorthAmericaCarFactory();
        Car northAmericaCar = northAmericaFactory.createCar();
        CarSpecification northAmericaSpec = northAmericaFactory.createSpecification();

        northAmericaCar.assemble();
        northAmericaSpec.display();

        // Creating cars for Europe
        CarFactory europeFactory = new EuropeCarFactory();
        Car europeCar = europeFactory.createCar();
        CarSpecification europeSpec = europeFactory.createSpecification();

        europeCar.assemble();
        europeSpec.display();
    }
}
