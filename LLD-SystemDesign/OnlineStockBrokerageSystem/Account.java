package OnlineStockBrokerageSystem;

public class Account {
    private double balance;

    public synchronized void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public synchronized boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized double getBalance() { return balance; }
}
