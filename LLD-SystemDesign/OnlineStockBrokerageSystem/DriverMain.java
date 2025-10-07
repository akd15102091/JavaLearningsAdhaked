package OnlineStockBrokerageSystem;

public class DriverMain {
    public static void main(String[] args) {
        StockBrokerageSystem system = StockBrokerageSystem.getInstance();

        // Setup via Factory
        User user = UserFactory.createUser("u1", "Alice");
        system.addUser(user);
        user.getAccount().deposit(10000);

        system.addStock(StockFactory.createStock("AAPL", "Apple", 150));
        system.addStock(StockFactory.createStock("TSLA", "Tesla", 700));

        system.showAccountBalance("u1");

        // Place Orders via Command Pattern
        system.submitOrder(new BuyOrder("u1", "AAPL", 20));
        system.submitOrder(new BuyOrder("u1", "TSLA", 5));

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        system.submitOrder(new SellOrder("u1", "AAPL", 10));

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        system.showAccountBalance("u1");
        system.showPortfolio("u1");

        system.shutdown();
    }
}
