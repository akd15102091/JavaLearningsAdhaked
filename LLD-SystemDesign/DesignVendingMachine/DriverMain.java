package DesignVendingMachine;

import DesignVendingMachine.PaymentStrategy.CoinPayment;
import DesignVendingMachine.PaymentStrategy.NotePayment;

public class DriverMain {
    public static void main(String[] args) {
        VendingMachine machine = VendingMachine.getInstance();

        Product coke = new Product("Coke", 25);
        Product chips = new Product("Chips", 35);
        
        machine.restock(coke, 10);
        machine.restock(chips, 5);
        
        // Simulating users purchasing items concurrently
        new Thread(() -> machine.purchaseOrder(coke, new CoinPayment(), 30)).start();
        new Thread(() -> machine.purchaseOrder(chips, new NotePayment(), 40)).start();
    }
}
