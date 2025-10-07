package OnlineStockBrokerageSystem;

public class StockFactory {
    public static Stock createStock(String symbol, String name, double price) {
        return new Stock(symbol, name, price);
    }
}