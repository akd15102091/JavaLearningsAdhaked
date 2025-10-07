package DesignOnlineStockBrokerage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import DesignOnlineStockBrokerage.PortFolio;

@SuppressWarnings("unused")
public class PortfolioService {
    private final Map<String, PortFolio> portfolios;
    private static PortfolioService instance;

    private PortfolioService(){
        this.portfolios = new ConcurrentHashMap<>();
    }

    public static synchronized PortfolioService getInstance(){
        if(instance == null){
            instance = new PortfolioService();
        }

        return instance;
    }

    public PortFolio getUserPortFolio(String userId){
        return this.portfolios.getOrDefault(userId, null);
    }

    public void addStocksToPortfolio(String userId, String symbol, int quantity, double price){
        PortFolio portFolio = this.portfolios.computeIfAbsent(userId, k -> new PortFolio(userId));
        portFolio.addStocksToPortfolio(symbol, quantity, price);
        return ;
    }

    public void removeStocksFromPortfolio(String userId, String symbol, int quantity) throws Exception{
        PortFolio portFolio = this.portfolios.computeIfAbsent(userId, k -> new PortFolio(userId));
        portFolio.sellStocksFromPortfolio(symbol, quantity);
        return ;
    }


}

