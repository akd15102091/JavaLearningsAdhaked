package OnlineStockBrokerageSystem;

import java.util.*;

public class Portfolio {
    private final Map<String, Integer> holdings = new HashMap<>();

    public synchronized void addStock(String symbol, int qty) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + qty);
    }

    public synchronized boolean removeStock(String symbol, int qty) {
        int current = holdings.getOrDefault(symbol, 0);
        if (current >= qty) {
            if (current == qty) holdings.remove(symbol);
            else holdings.put(symbol, current - qty);
            return true;
        }
        return false;
    }

    public synchronized Map<String, Integer> getHoldings() {
        return new HashMap<>(holdings);
    }

    public synchronized double getPortfolioValue(Map<String, Stock> stockMap) {
        double total = 0.0;
        for (String symbol : holdings.keySet()) {
            Stock stock = stockMap.get(symbol);
            if (stock != null) {
                total += stock.getPrice() * holdings.get(symbol);
            }
        }
        return total;
    }
}