package OnlineStockBrokerageSystem;

public class UserFactory {
    public static User createUser(String userId, String name) {
        return new User(userId, name);
    }
}