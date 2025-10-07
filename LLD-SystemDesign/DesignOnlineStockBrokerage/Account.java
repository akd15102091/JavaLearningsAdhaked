package DesignOnlineStockBrokerage;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unused")
public class Account {
    private final String userId;
    private final BankName bank;
    private final String accountNumber;
    private double balance;
    private ReentrantLock lock;

    public Account(String userId, BankName bank, double openingBalace){
        this.userId = userId;
        this.bank = bank;
        this.accountNumber = "A-"+userId.substring(0,4)+UUID.randomUUID().toString();
        this.balance = openingBalace;
        this.lock = new ReentrantLock();
    }

    public double getBalance(){
        return this.balance;
    }

    public void deposit(double amount){
        this.lock.lock();
        try{
            this.balance += amount;
        } finally {
            this.lock.unlock();
        }
        
    }

    public void withdraw(double amount) throws Exception{
        if(this.balance < amount){
            throw new Exception("Error: Insufficient funds.");
        }
        this.balance -= amount;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }
}
