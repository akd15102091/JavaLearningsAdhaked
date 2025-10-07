package DesignOnlineStockBrokerage;

import java.util.UUID;

@SuppressWarnings("unused")
public class Order {
    private final String orderId;
    private final int quantity;
    private final String symbol;
    private final double price;
    private final String userId;
    private final OrderType orderType;
    private final long createdAt;
    private OrderStatus orderStatus;
    
    public Order(String symbol, int quantity, double price, String userId, OrderType type, OrderStatus status){
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
        this.orderType = type;
        this.orderId = "O-"+UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.orderStatus = status;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", quantity=" + quantity + ", symbol=" + symbol + ", price=" + price
                + ", userId=" + userId + ", orderType=" + orderType + "]";
    }

    public void updateStatus(OrderStatus status){
        this.orderStatus = status;
    }
}

