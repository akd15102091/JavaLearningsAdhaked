package DesignACoffeeVendingMachine;

public class CoffeeMachine {
    private boolean isOn = true;

    public synchronized void processOrder(String beverageType) {
        if (!isOn) {
            System.out.println("Machine is off.");
            return;
        }

        Beverage beverage = BeverageFactory.getBeverage(beverageType);
        if (beverage.prepare()) {
            System.out.println("Enjoy your " + beverage.getName() + "!");
        }
    }

    public void turnOff() {
        isOn = false;
        System.out.println("Coffee Machine turned off.");
    }

    public void turnOn() {
        isOn = true;
        System.out.println("Coffee Machine is ready.");
    }
}

