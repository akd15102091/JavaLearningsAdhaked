import java.util.List;

public class DispenseProductState implements States{


    public DispenseProductState(VendingMachine vendingMachine, ItemShelf itemShelf){
        System.out.println("Vending machine is in Dispense Product State");
        dispenseProduct(vendingMachine,itemShelf);
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickOnInsertCoinButton'");
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine vendingMachine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickOnStartProductSelectionButton'");
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertCoin'");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine,int code) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refundFullMoney'");
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, ItemShelf itemShelf) {
        itemShelf.setSoldOut(true);
        System.out.println("dispensing the product.");
        vendingMachine.setState(new IdleState());
        return itemShelf.getItem();
    }

    
}
