package OnlineStockBrokerageSystem;

public class User {
    private final String userId;
    private final String name;
    private final Account account;
    private final Portfolio portfolio;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.account = new Account();
        this.portfolio = new Portfolio();
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public Account getAccount() { return account; }
    public Portfolio getPortfolio() { return portfolio; }
}
