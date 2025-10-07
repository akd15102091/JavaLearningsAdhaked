import java.util.List;

public  class ProductSelectionState implements States{


    public ProductSelectionState(){
        System.out.println("Vending machine is in Product Selection State");
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        
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
        ItemShelf itemShelf = vendingMachine.getInventory().getItemByCode(code);
        if(itemShelf.isSoldOut()){
            System.out.println("Sorry, Item sold out");
            refundFullMoney(vendingMachine);
        }
        else{
            int pricePaidByUser =0;
            List<Coin>coinList = vendingMachine.getCoinsList();
            for(Coin coin:coinList){
                pricePaidByUser+=coin.value;
            }
            
            if(pricePaidByUser >= itemShelf.getItem().getPrice()){
                int refundAmount = pricePaidByUser - itemShelf.getItem().getPrice();
                getChange(vendingMachine, refundAmount);
                vendingMachine.setState(new DispenseProductState(vendingMachine,itemShelf));
            }
            else{
                refundFullMoney(vendingMachine);
            }
        }
    }

    @Override
    public int getChange(VendingMachine vendingMachine,int refundAmount) {
        System.out.println("returned the change coin in dispense tray : "+refundAmount);
        return refundAmount;
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        System.out.println("full money refund");
        vendingMachine.setState(new IdleState());
        return vendingMachine.getCoinsList();
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, ItemShelf itemShelf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispenseProduct'");
    }
    
}
