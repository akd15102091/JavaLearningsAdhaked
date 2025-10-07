import java.util.List;

public class HasMoneyState implements States{

    
    public HasMoneyState(){
        System.out.println("vending machine is in HasMoney State.");
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickOnInsertCoinButton'");
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) {
        vendingMachine.setState(new ProductSelectionState());
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
        vendingMachine.getCoinsList().add(coin);
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, int code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectProduct'");
    }

    @Override
    public int getChange(VendingMachine vendingMachine,int refundAmount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChange'");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        vendingMachine.setState(new IdleState());
        return vendingMachine.getCoinsList();
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, ItemShelf itemShelf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispenseProduct'");
    }
    
}
