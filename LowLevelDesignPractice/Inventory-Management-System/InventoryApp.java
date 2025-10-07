import java.util.ArrayList;
import java.util.List;

public class InventoryApp {

    UserController userController;
    OrderController orderController;
    WareHouseController wareHouseController;

    public void initControllers(){
        this.userController = new UserController();
        this.orderController = new OrderController();
        this.wareHouseController = new WareHouseController();
    }

    private void createUser(){
        userController.createUser(new User(1, "Ashwin"));
        userController.getUser(1).setAddress(new Address(560036, "Bengaluru"));
    }

    private void createWareHouse(){
        WareHouse wareHouse = new WareHouse(new Address(560036, "Bengaluru"), 1);
        wareHouseController.addWareHouse(wareHouse);
        List<Product>prodList1 = new ArrayList();
        List<Product>prodList2 = new ArrayList();

        List<ProductShelf> productCategories = new ArrayList<>();
        for(int i=1;i<=5;i++){
            Product prod1 = new Product(20+i,"Pepsi");
            prodList1.add(prod1);
        }
        ProductShelf productCategory1 = new ProductShelf(20,"Pepsi",prodList1,20);

        for(int i=1;i<=15;i++){
            Product prod1 = new Product(30+i,"Namkeen");
            prodList2.add(prod1);
        }

        ProductShelf productCategory2 = new ProductShelf(30,"Namkeen",prodList2,150);

        productCategories.add(productCategory1);
        productCategories.add(productCategory2);

        wareHouse.fillInventory(productCategories);
    }
    public static void main(String[] args) {
        InventoryApp iApp = new InventoryApp();
        iApp.initControllers() ;
        iApp.createUser();
        iApp.createWareHouse();

        System.out.println("user with 1 comes ");
        User user = iApp.userController.getUser(1);
        System.out.println("user details :- "+ user.toString());


        WareHouse wareHouse = iApp.wareHouseController.getWareHouse(user.getAddress()) ;
        System.out.println("Inventory Details : ");
        wareHouse.displayInventory();

        //user add 2 pepsi and 1 namkeen
        ProductShelf pepsiProdcut = wareHouse.getInventory().getProdShelfById(20) ;
        ProductShelf namkeenProdcut = wareHouse.getInventory().getProdShelfById(30) ;
        user.addToCart(pepsiProdcut, 2);
        user.addToCart(namkeenProdcut, 1);

        // removing items for inventory, will reset inventory if order is failed
        wareHouse.removeInventory(20, 2);
        wareHouse.removeInventory(30, 1);

        Order order = new Order(100, user, OrderStatus.PENDING, user.getCart().getTotalCost()) ;
        order.payOrder();

        System.out.println("displaying updated inventory");
        System.out.println("Inventory Details : ");
        wareHouse.displayInventory();





        }
           
}
