package OnlineStockBrokerageSystem;

// ======= Order Commands =======
public abstract class Order implements Command {
    protected final String userId;
    protected final String stockSymbol;
    protected final int quantity;

    public Order(String userId, String stockSymbol, int quantity) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
    }
}