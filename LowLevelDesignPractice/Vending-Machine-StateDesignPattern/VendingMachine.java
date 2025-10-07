import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    States state;
    Inventory inventory;
    List<Coin>coinsList = new ArrayList();

    
    public VendingMachine() {
        this.state = new IdleState();
    }

    public States getState() {
        return state;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setState(States state) {
        this.state = state;
    }

    public List<Coin> getCoinsList() {
        return coinsList;
    }

    public void setCoinsList(List<Coin> coinsList) {
        this.coinsList = coinsList;
    }

    
}
