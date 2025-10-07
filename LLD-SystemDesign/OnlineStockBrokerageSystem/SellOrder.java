package OnlineStockBrokerageSystem;

public class SellOrder extends Order {
    public SellOrder(String userId, String stockSymbol, int quantity) {
        super(userId, stockSymbol, quantity);
    }

    @Override
    public void execute() {
        StockBrokerageSystem system = StockBrokerageSystem.getInstance();
        User user = system.getUser(userId);
        Stock stock = system.getStock(stockSymbol);
        if (user == null || stock == null) return;

        if (user.getPortfolio().removeStock(stockSymbol, quantity)) {
            double totalValue = stock.getPrice() * quantity;
            user.getAccount().deposit(totalValue);
            System.out.println("SELL order executed: " + quantity + " " + stockSymbol);
        } else {
            System.out.println("SELL order failed: insufficient stock");
        }
    }
}