import java.util.ArrayList;
import java.util.List;

public class User {
    int userId;
    String userName;
    Cart cart;
    Address address;
    List<Integer> orderIds = new ArrayList<>();

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.cart = new Cart();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrderIds(List<Integer> orderIds) {
        this.orderIds = orderIds;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + "]";
    }

    public void addToCart(ProductShelf productShelf, int count){
        this.cart.addItem(productShelf, count);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    
}
