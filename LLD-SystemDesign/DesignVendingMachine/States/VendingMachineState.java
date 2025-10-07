package DesignVendingMachine.States;

import DesignVendingMachine.Product;
import DesignVendingMachine.VendingMachine;
import DesignVendingMachine.PaymentStrategy.PaymentStrategy;

public interface VendingMachineState {
    public void handle(VendingMachine machine, Product product, PaymentStrategy paymentStrategy, int amount) ;
} 
