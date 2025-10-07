package DesignVendingMachine.States;

import DesignVendingMachine.Product;
import DesignVendingMachine.VendingMachine;
import DesignVendingMachine.PaymentStrategy.PaymentStrategy;

public class DispensingState implements VendingMachineState{

    @Override
    public void handle(VendingMachine machine, Product product, PaymentStrategy paymentStrategy, int amount) {
        machine.getInventory().dispenseProduct(product);
        int change = amount - product.getPrice();
        System.out.println("Dispensing " + product.getName() + " and returning change: " + change);
        machine.setState(new IdleState());
    }
    
}
