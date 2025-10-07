package DesignVendingMachine.States;

import DesignVendingMachine.Product;
import DesignVendingMachine.VendingMachine;
import DesignVendingMachine.PaymentStrategy.PaymentStrategy;

public class ProcessingPaymentState implements VendingMachineState{

    @Override
    public void handle(VendingMachine machine, Product product, PaymentStrategy paymentStrategy, int amount) {
        if (amount < product.getPrice()) {
            System.out.println("Insufficient funds!");
            machine.setState(new IdleState());
            return;
        }
        if (paymentStrategy.pay(amount)) {
            machine.setState(new DispensingState());
            machine.handleState(product, paymentStrategy, amount);
        }
    }
    
}
