import java.util.List;

public class IdleState implements States{

    public IdleState(){
        System.out.println("Vending machine is in Idle state");
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        vendingMachine.setState(new HasMoneyState());
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) {
        throw new UnsupportedOperationException("Unimplemented method 'clickOnStartProductSelectionButton'");
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine,ItemShelf itemShelf) {
        throw new UnsupportedOperationException("Unimplemented method 'dispenseProduct'");

    }

    @Override
    public int getChange(VendingMachine vendingMachine,int refundAmount) {
        throw new UnsupportedOperationException("Unimplemented method 'refundFullMoney'");
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
        throw new UnsupportedOperationException("Unimplemented method 'insertCoin'");

    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        throw new UnsupportedOperationException("Unimplemented method 'refundFullMoney'");
 
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine,int code) {
        
    }
    
}
