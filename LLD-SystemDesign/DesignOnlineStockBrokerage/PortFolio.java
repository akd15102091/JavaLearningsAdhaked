package DesignOnlineStockBrokerage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PortFolio {
    private final String userId;
    private final Map<String, Integer> quantityMap; // symbol, quantity
    private final Map<String, Double> averagePriceMap; // symbol, average bought price

    public PortFolio(String userId){
        this.userId = userId;
        this.quantityMap = new ConcurrentHashMap<>();
        this.averagePriceMap = new ConcurrentHashMap<>();
    }

    public Map<String, Integer> getQuantityMap(){
        return new HashMap<>(this.quantityMap);
    }

    public Map<String, Double> getAvgPriceMap(){
        return new HashMap<>(this.averagePriceMap);
    }

    public void addStocksToPortfolio(String symbol, int quantity, double price){
        int existingQuantity = this.quantityMap.getOrDefault(symbol, 0);
        double avgPrice = this.averagePriceMap.getOrDefault(symbol, 0.0);

        int updatedQuantity = quantity + existingQuantity;
        double updatedAvgPrice = ((quantity*price) + (existingQuantity*avgPrice))/(1.0*updatedQuantity);

        this.quantityMap.put(symbol, updatedQuantity);
        this.averagePriceMap.put(symbol, updatedAvgPrice);
    }

    public void sellStocksFromPortfolio(String symbol, int quantity) throws Exception{
        int existingQuantity = this.quantityMap.getOrDefault(symbol, 0);
        if(existingQuantity < quantity){
            throw new Exception("Insufficient stock quantity.");
        }
        int updatedQuantity = existingQuantity - quantity;
        this.quantityMap.put(symbol, updatedQuantity);
    }

    @Override
    public String toString() {
        return "PortFolio [userId=" + userId + ", quantityMap=" + quantityMap + ", averagePriceMap=" + averagePriceMap
                + "]";
    }
}

