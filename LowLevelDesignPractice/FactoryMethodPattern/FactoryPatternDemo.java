
/*
 * The Factory Design Pattern is a creational design pattern used to create objects without specifying 
   the exact class of the object being created. It promotes loose coupling and encapsulation by 
   delegating the instantiation logic to a separate factory class.

    Implementation of Factory Pattern in Java:
        Let's implement a Shape Factory that returns different shape objects 
        (Circle, Rectangle, Square) based on user input.
 */


interface Shape {
    void draw() ;    
} 

class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("Drawing a circle...");
    }
}

class Rectangle implements Shape{
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle...");
    }
}

class Square implements Shape{
    @Override
    public void draw() {
        System.out.println("Drawing a square...");
    }
}

class ShapeFactory {
    // Factory method to return an instance of Shape

    public Shape getShape(String shapeType){
        if (shapeType == null) {
            return null;
        }
        switch (shapeType.toLowerCase()) {
            case "circle":
                return new Circle();

            case "rectangle":
                return new Rectangle();
            
            case "square":
                return new Square();
        
            default:
                throw new IllegalArgumentException("Invalid shape type: " + shapeType) ;
        }
    }
}



public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        
        // Get objects using factory and call draw method
        Shape shape1 = shapeFactory.getShape("circle");
        shape1.draw();

        Shape shape2 = shapeFactory.getShape("rectangle");
        shape2.draw();

        Shape shape3 = shapeFactory.getShape("square");
        shape3.draw();
    }
}
