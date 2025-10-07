package DesignVendingMachine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import DesignVendingMachine.PaymentStrategy.PaymentStrategy;
import DesignVendingMachine.States.IdleState;
import DesignVendingMachine.States.VendingMachineState;

public class VendingMachine {
    private static VendingMachine instance;
    private VendingMachineState state;
    private final Inventory inventory;
    private final Lock lock = new ReentrantLock();

    private VendingMachine(){
        this.inventory = new Inventory();
        this.state = new IdleState();
    }

    public synchronized static VendingMachine getInstance(){
        if(instance==null){
            instance = new VendingMachine();
        }
        return instance;
    }

    public void handleState(Product product, PaymentStrategy paymentStrategy, int amount){
        state.handle(this, product, paymentStrategy, amount);
    }

    public void purchaseOrder(Product product, PaymentStrategy paymentStrategy, int amount){
        lock.lock();
        try{
            state.handle(this, product, paymentStrategy, amount);
        }finally{
            lock.unlock();
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public void restock(Product product, int quantity) {
        inventory.addProduct(product, quantity);
        System.out.println("Restocked " + product.getName() + " with " + quantity + " units.");
    }

    public VendingMachineState getState() {
        return state;
    }

    

}
