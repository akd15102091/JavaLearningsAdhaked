package DesignVendingMachine.States;

import DesignVendingMachine.Product;
import DesignVendingMachine.VendingMachine;
import DesignVendingMachine.PaymentStrategy.PaymentStrategy;

public class OutOfStockState implements VendingMachineState{

    @Override
    public void handle(VendingMachine machine, Product product, PaymentStrategy paymentStrategy, int amount) {
        System.out.println("Product out of stock!");
        machine.setState(new IdleState());
    }
    
}
