package DesignOnlineStockBrokerage.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import DesignOnlineStockBrokerage.Account;

@SuppressWarnings("unused")
public class AccountService {
    private final Map<String, Map<String, Account>> accounts; // debit accounts <UserId, <accountNumber, Acocunt>>
    private final Map<String, Account> creditAccount;
    private static AccountService instance;

    private AccountService(){
        this.accounts = new ConcurrentHashMap<>();
        this.creditAccount = new ConcurrentHashMap<>();
    }

    public static synchronized AccountService getInstance(){
        if(instance == null){
            instance = new AccountService();
        }

        return instance;
    }

    public void addAccount(String userId, Account account){
        this.accounts.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(account.getAccountNumber(), account);
    }

    public void updateCreditAccount(String userId, Account creditAccount){
        this.creditAccount.put(userId, creditAccount);
    }

    public Account getCreditAccount(String userId){
        return this.creditAccount.getOrDefault(userId, null);
    }

    public List<Account> getDebitAccount(String userId){
        return new ArrayList<>(this.accounts.getOrDefault(userId, new ConcurrentHashMap<>()).values()) ;
    }

    public void depositToAccount(String userId, String accountNumber, double amount) throws Exception{
        Account account = this.accounts.getOrDefault(userId, new ConcurrentHashMap<>()).getOrDefault(accountNumber, null);
        if(account == null){
            throw new Exception("Account doesn't exist for this user.");
        }
        account.deposit(amount);
    }

    public void withdrawFromAccount(String userId, String accountNumber, double amount) throws Exception{
        Account account = this.accounts.getOrDefault(userId, new ConcurrentHashMap<>()).getOrDefault(accountNumber, null);
        if(account == null){
            throw new Exception("Account doesn't exist for this user.");
        }
        account.withdraw(amount);
    }
}

