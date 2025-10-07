import java.util.List;

public interface States {

    public void clickOnInsertCoinButton(VendingMachine vendingMachine);
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine);
    public void insertCoin(VendingMachine vendingMachine, Coin coin);
    public void selectProduct(VendingMachine vendingMachine, int code);
    public int getChange(VendingMachine vendingMachine,int refundAmount);
    public List<Coin> refundFullMoney(VendingMachine vendingMachine);
    public Item dispenseProduct(VendingMachine vendingMachine, ItemShelf itemShelf);
    
}