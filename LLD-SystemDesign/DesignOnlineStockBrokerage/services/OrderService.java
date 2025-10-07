package DesignOnlineStockBrokerage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignOnlineStockBrokerage.Order;
import DesignOnlineStockBrokerage.OrderStatus;
import DesignOnlineStockBrokerage.OrderType;

@SuppressWarnings("unused")
public class OrderService {
    private final Map<String, List<Order>> orders;
    private static OrderService instance;

    private OrderService(){
        this.orders = new ConcurrentHashMap<>();
    }

    public static synchronized OrderService getInstance(){
        if(instance == null){
            instance = new OrderService();
        }

        return instance;
    }

    public Order createOrder(String symbol, int quantity, double price, String userId, OrderType type, OrderStatus status){
        Order order = new Order(symbol, quantity, price, userId, type, status);
        this.orders.computeIfAbsent(userId, k -> new ArrayList<>()).add(order);   
        return order;
    }
}

