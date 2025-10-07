package DesignOnlineStockBrokerage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignOnlineStockBrokerage.Transaction;

@SuppressWarnings("unused")
public class TransactionService {
    private final Map<String, List<Transaction>> transactions;
    private static TransactionService instance;

    private TransactionService(){
        this.transactions = new ConcurrentHashMap<>();
    }

    public static synchronized TransactionService getInstance(){
        if(instance == null){
            instance = new TransactionService();
        }

        return instance;
    }

    public void addTransaction(String userId, Transaction transaction){
        this.transactions.computeIfAbsent(userId, k -> new ArrayList<>()).add(transaction);
    }

    public List<Transaction> getUserTransaction(String userId){
        return this.transactions.getOrDefault(userId, new ArrayList<>());
    }
}


