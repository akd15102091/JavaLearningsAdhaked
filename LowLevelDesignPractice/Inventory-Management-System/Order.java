

public class Order {
    int orderId;
    User user;
    double cost=0;
    // Map<Integer,Integer> mp = new HashMap<>(); // cateogory id vs count; to display details
    OrderStatus status;
    Invoice invoice;
    Payment payment;
    public Order(int orderId, User user, OrderStatus status, double cost) {
        this.orderId = orderId;
        this.user = user;
        this.status = status;
        this.cost=cost;
        this.invoice = new Invoice(cost) ;
        this.payment = new Payment();
    }

    public void payOrder(){
        this.payment.pay(cost);
    }

    
}
