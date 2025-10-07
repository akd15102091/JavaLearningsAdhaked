package OnlineStockBrokerageSystem;

public class BuyOrder extends Order {
    public BuyOrder(String userId, String stockSymbol, int quantity) {
        super(userId, stockSymbol, quantity);
    }

    @Override
    public void execute() {
        StockBrokerageSystem system = StockBrokerageSystem.getInstance();
        User user = system.getUser(userId);
        Stock stock = system.getStock(stockSymbol);
        if (user == null || stock == null) return;

        double totalCost = stock.getPrice() * quantity;
        if (user.getAccount().withdraw(totalCost)) {
            user.getPortfolio().addStock(stockSymbol, quantity);
            System.out.println("BUY order executed: " + quantity + " " + stockSymbol);
        } else {
            System.out.println("BUY order failed: insufficient funds");
        }
    }
}
