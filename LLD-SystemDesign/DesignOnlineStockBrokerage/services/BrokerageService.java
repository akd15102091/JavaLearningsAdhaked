package DesignOnlineStockBrokerage.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignOnlineStockBrokerage.Account;
import DesignOnlineStockBrokerage.Order;
import DesignOnlineStockBrokerage.OrderStatus;
import DesignOnlineStockBrokerage.OrderType;
import DesignOnlineStockBrokerage.PortFolio;
import DesignOnlineStockBrokerage.Stock;
import DesignOnlineStockBrokerage.User;

@SuppressWarnings("unused")
public class BrokerageService {
    private AccountService accountService;
    private OrderService orderService;
    private PortfolioService portfolioService;
    private StockService stockService;
    private TransactionService transactionService;
    private static BrokerageService instance;

    // we can create a separate service for user, but for time being, I am using this as user service
    private Map<String, User> users;

    private BrokerageService(){
        this.accountService = AccountService.getInstance();
        this.orderService = OrderService.getInstance();
        this.portfolioService = PortfolioService.getInstance();
        this.stockService = StockService.getInstance();
        this.transactionService = TransactionService.getInstance();

        this.users = new ConcurrentHashMap<>();
    }

    public static synchronized BrokerageService getInstance(){
        if(instance == null){
            instance = new BrokerageService();
        }
        return instance;
    }

    public User registerUser(String email, String name, String mobile){
        User user = new User(email, name, mobile);
        this.users.put(email, user); // we kept email as userId
        return user;
    }

    public Account addAccount(String userId, Account account){
        this.accountService.addAccount(userId, account);
        return account;
    }

    // update debit account
    public Account updateCreditAccount(String userId, Account account ) {
        this.accountService.updateCreditAccount(userId, account);
        return account;
    }
    

    // buy stocks
    public boolean buyShares(String symbol, String userId, int numOfShareToBuy, Account account) throws Exception{
        Stock stock = this.stockService.getStock(symbol);
        Order order = this.orderService.createOrder(symbol, numOfShareToBuy, stock.getPrice(),  userId, OrderType.BUY, OrderStatus.SUBMITTED_TO_EXCHANGE);
        // whole functions need to be executed in single transaction
        double price = numOfShareToBuy*(stock.getPrice()) ;
        if(price > account.getBalance()){
            order.updateStatus(OrderStatus.CANCELLED);
            throw new Exception("Insufficient funds to complate this buy order");
        }
        stock.bought(numOfShareToBuy); // stock quantity updated
        this.accountService.withdrawFromAccount(userId, account.getAccountNumber(), price);// account balance updated
        this.portfolioService.addStocksToPortfolio(userId, symbol, numOfShareToBuy, price); // add to portfolio
        order.updateStatus(OrderStatus.COMPLETED);
        return true;
    }

    // sell some stocks
    public boolean sellShares(String symbol, String userId, int numOfShareToSell) throws Exception{
        Stock stock = this.stockService.getStock(symbol);
        Order order = this.orderService.createOrder(symbol, numOfShareToSell, stock.getPrice(), userId,OrderType.SELL, OrderStatus.SUBMITTED_TO_EXCHANGE);
        // whole functions need to be executed in single transaction
        double price = numOfShareToSell*(stock.getPrice()) ;
        Account creditAccount = this.accountService.getCreditAccount(userId);
        if(creditAccount == null){
            throw new Exception("Credit account is not setup yet. Can't withdraw and add fund to your accounts");
        }
        stock.sold(numOfShareToSell); // stock quantity updated
        this.accountService.depositToAccount(userId, creditAccount.getAccountNumber(), price); // account balance updated
        this.portfolioService.removeStocksFromPortfolio(userId, symbol, numOfShareToSell); // remove from portfolio
        return true;
    }

    // viewPortfolio
    public void viewPortfolio(String userId){
        PortFolio portFolio = this.portfolioService.getUserPortFolio(userId);
        if(portFolio == null){
            System.out.println("There is no stocks in portfolio.");
            return;
        }

        Map<String, Integer> quantity = portFolio.getQuantityMap();
        Map<String, Double> avgPrice = portFolio.getAvgPriceMap();

        System.out.println("\n"+userId+" portfolio:- ");
        for(Map.Entry<String, Integer> entry : quantity.entrySet()){
            System.out.println(entry.getKey()+" -> "+entry.getValue()+" shares, total price: "+avgPrice.get(entry.getKey()));
        }
        System.out.println("\n\n");
    }
}
