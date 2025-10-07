/*
 * Scenario: Let's create a simple Order Processing Service that follows the SOLID principles.
 */

class Order{
    private String orderId;
    private String product;
    private int quantity;

    public Order(String orderId, String product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", product=" + product + ", quantity=" + quantity + "]";
    }
} 



interface OrderRepository {
    void saveOrder(Order order) ;
}

class DatabaseOrderRepository implements OrderRepository{
    @Override
    public void saveOrder(Order order) {
        System.out.println("Saving order to database");
    }
}




interface NotificationService {
    void notifyCustomer(String message);
}

class EmailNotificationService implements NotificationService{
    @Override
    public void notifyCustomer(String message) {
        System.out.println("Notifying customer using email with message : "+ message);
    }
    
}



class OrderProcessor{
    OrderRepository orderRepository;
    NotificationService notificationService;

    public OrderProcessor(OrderRepository orderRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void processOrder(Order order){
        System.out.println("\n Order is processing....");
        this.orderRepository.saveOrder(order);
        this.notificationService.notifyCustomer("Order is processed");
        System.out.println("\n Order is processed....");
    }

}


class OrderServiceApp{
    public static void main(String[] args) {
        OrderRepository orderRepository = new DatabaseOrderRepository();
        NotificationService notificationService = new EmailNotificationService() ;

        OrderProcessor orderProcessor = new OrderProcessor(orderRepository, notificationService);

        Order order1 = new Order("order123", "Books", 5) ;

        orderProcessor.processOrder(order1);
    }
}






/*
 * 
 * How It Follows SOLID Principles
    SRP:
        Order → Represents an order
        OrderRepository → Handles persistence
        EmailNotificationService → Handles notifications
        OrderProcessor → Contains business logic
    
    
    OCP:   
        If we need a new notification type (e.g., SMS), we can create an SMSNotificationService 
        without modifying OrderProcessor.


    LSP:
        EmailNotificationService correctly implements NotificationService and can be swapped 
        with any other notification type.


    ISP:
        OrderRepository and NotificationService are separate, so they don’t force 
        unnecessary dependencies.


    DIP:
        OrderProcessor depends on interfaces (OrderRepository, NotificationService) 
        instead of concrete implementations.

 */