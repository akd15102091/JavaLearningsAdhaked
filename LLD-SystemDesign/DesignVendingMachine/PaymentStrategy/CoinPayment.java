package DesignVendingMachine.PaymentStrategy;

public class CoinPayment implements PaymentStrategy{

    @Override
    public boolean pay(int amount) {
        System.out.println("Payment done using Coins: " + amount);
        return true;
    }
    
}
