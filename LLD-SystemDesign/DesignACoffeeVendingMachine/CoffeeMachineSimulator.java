package DesignACoffeeVendingMachine;

public class CoffeeMachineSimulator {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();

        Runnable order1 = () -> machine.processOrder("Espresso");
        Runnable order2 = () -> machine.processOrder("Cappuccino");

        Thread t1 = new Thread(order1);
        Thread t2 = new Thread(order2);

        t1.start();
        t2.start();
    }
}
