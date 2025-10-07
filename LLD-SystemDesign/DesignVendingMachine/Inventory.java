package DesignVendingMachine;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<Product, Integer> stock = new HashMap<>();;
    
    public synchronized void addProduct(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public synchronized boolean isAvailable(Product product) {
        return stock.getOrDefault(product, 0) > 0;
    }

    public synchronized void dispenseProduct(Product product) {
        if (isAvailable(product)) {
            stock.put(product, stock.get(product) - 1);
        }
    }
}
