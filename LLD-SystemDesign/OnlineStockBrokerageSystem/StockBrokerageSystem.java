package OnlineStockBrokerageSystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// ======= Singleton Pattern =======
public class StockBrokerageSystem {
    private static volatile StockBrokerageSystem instance;
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, Stock> stocks = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private StockBrokerageSystem() {}

    public static StockBrokerageSystem getInstance() {
        if (instance == null) {
            synchronized (StockBrokerageSystem.class) {
                if (instance == null) {
                    instance = new StockBrokerageSystem();
                }
            }
        }
        return instance;
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void submitOrder(Command command) {
        executor.submit(() -> command.execute());
    }

    public void showPortfolio(String userId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        Portfolio portfolio = user.getPortfolio();
        System.out.println("Portfolio of " + user.getName() + ":");
        for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double price = stocks.get(symbol).getPrice();
            System.out.println(symbol + " - Qty: " + qty + " - Price: $" + price);
        }
        double value = portfolio.getPortfolioValue(stocks);
        System.out.println("Total Portfolio Value: $" + value);
    }

    public void showAccountBalance(String userId) {
        User user = users.get(userId);
        if (user != null) {
            System.out.println("Balance of " + user.getName() + ": $" + user.getAccount().getBalance());
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Shutdown interrupted.");
        }
    }
}
