package DesignVendingMachine.States;

import DesignVendingMachine.Product;
import DesignVendingMachine.VendingMachine;
import DesignVendingMachine.PaymentStrategy.PaymentStrategy;

public class IdleState implements VendingMachineState {
    // public IdleState(){}

    @Override
    public void handle(VendingMachine machine, Product product, PaymentStrategy paymentStrategy, int amount) {
        if(!machine.getInventory().isAvailable(product)){
            machine.setState(new OutOfStockState());
            machine.handleState(product, paymentStrategy, amount);
            return;
        }

        machine.setState(new ProcessingPaymentState());
        machine.handleState(product, paymentStrategy, amount);
    }
    
}
