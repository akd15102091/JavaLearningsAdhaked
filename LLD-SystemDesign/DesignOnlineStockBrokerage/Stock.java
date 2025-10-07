package DesignOnlineStockBrokerage;

import java.util.concurrent.locks.ReentrantLock;

public class Stock {
    // Assuming currency as dollar
    private final String symbol;
    private final String company;
    private double price;
    private int totalQuantity;
    private int availableQuantity;
    private ReentrantLock lock;

    public Stock(String symbol, String company, double initialPrice, int quantity){
        this.symbol = symbol;
        this.company = company;
        this.price = initialPrice;
        this.totalQuantity = quantity;
        this.availableQuantity = quantity;
        this.lock = new ReentrantLock();
    }

    public double getPrice(){
        return this.price;
    }

    public void bought(int quantity){
        this.lock.lock();
        try{
            this.availableQuantity -= quantity;
        } finally {
            this.lock.unlock();
        }
    }
    public void sold(int quantity){
        this.lock.lock();
        try{
            this.availableQuantity += quantity;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Stock [symbol=" + symbol + ", company=" + company + ", price=" + price + ", totalQuantity="
                + totalQuantity + ", availableQuantity=" + availableQuantity + "]";
    }

    
}

