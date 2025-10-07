import java.util.ArrayList;
import java.util.List;

public class VendingMachineDriver {

    public void fillInventory(VendingMachine vendingMachine){
        System.out.println("Filling up the Inventory");
        List<ItemShelf> itemShelfList = new ArrayList();
        Inventory inventory = new Inventory();
        for(int i=0;i<5;i++){
            ItemShelf itemShelf = new ItemShelf();
            itemShelf.setItem(new Item(ItemType.PEPSI, 30));
            itemShelf.setCode(100+i);
            itemShelf.setSoldOut(false);
            itemShelfList.add(itemShelf);
        }
        for(int i=0;i<5;i++){
            ItemShelf itemShelf = new ItemShelf();
            itemShelf.setItem(new Item(ItemType.COKE, 40));
            itemShelf.setCode(200+i);
            itemShelf.setSoldOut(false);
            itemShelfList.add(itemShelf);
        }
        for(int i=0;i<5;i++){
            ItemShelf itemShelf = new ItemShelf();
            itemShelf.setItem(new Item(ItemType.ALMONDS, 150));
            itemShelf.setCode(300+i);
            itemShelf.setSoldOut(false);
            itemShelfList.add(itemShelf);
        }
        inventory.setItemShelfList(itemShelfList);
        vendingMachine.setInventory(inventory);
        System.out.println("---------------------------");
        vendingMachine.getInventory().displayInventory();
        System.out.println("---------------------------");
    }
    public static void main(String[] args) {
        VendingMachineDriver vendingMachineDriver = new VendingMachineDriver();
        VendingMachine vendingMachine = new VendingMachine();

        vendingMachineDriver.fillInventory(vendingMachine);
        try{
            System.out.println("user clicks on insert coin button");
            vendingMachine.getState().clickOnInsertCoinButton(vendingMachine);

            System.out.println("user insert 1 QUARTER  and 1 DIME (TOTAL = 25+10 =35)");
            vendingMachine.getState().insertCoin(vendingMachine, Coin.DIME);
            vendingMachine.getState().insertCoin(vendingMachine, Coin.QUARTER);

            System.out.println("user clicks on product selection button");
            vendingMachine.getState().clickOnStartProductSelectionButton(vendingMachine);

            System.out.println("USER choosing product with 101 code");
            vendingMachine.getState().selectProduct(vendingMachine, 101);
            System.out.println("---------------------------");
            vendingMachine.getInventory().displayInventory();
            System.out.println("---------------------------");

        } catch(Exception e){
            System.out.println("exception "+e);
        }
}
}
