package DesignOnlineStockBrokerage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignOnlineStockBrokerage.Stock;

@SuppressWarnings("unused")
public class StockService {
    private final Map<String, Stock> stocks;
    private static StockService instance;

    private StockService(){
        this.stocks = new ConcurrentHashMap<>();

        this.stocks.put("APPL", new Stock("APPL", "APPLE", 100, 10000));
        this.stocks.put("MSFT", new Stock("MSFT", "Microsoft", 120, 25000));
        this.stocks.put("ORCL", new Stock("ORCL", "ORACLE", 50, 7000));
    }

    public static synchronized StockService getInstance(){
        if(instance == null){
            instance = new StockService();
        }

        return instance;
    }

    public Stock getStock(String symbol){
        return this.stocks.getOrDefault(symbol, null);
    }

}
