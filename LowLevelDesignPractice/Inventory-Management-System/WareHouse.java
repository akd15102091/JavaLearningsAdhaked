import java.util.List;

public class WareHouse {
    
    Inventory inventory ;
    Address address;
    int wareHouseId;

    public WareHouse(Address address, int wareHouseId) {
        this.address = address;
        this.wareHouseId = wareHouseId;
        this.inventory = new Inventory();
    }

    public void fillInventory(List<ProductShelf> prodCatList){
        inventory.fill(prodCatList);
    }

    public void removeInventory(int categoryId, int count){
        for(int i=0;i<inventory.getProdShelfList().size();i++){
            ProductShelf productShelf = inventory.getProdShelfList().get(i);
            if(productShelf.getCategoryId() == categoryId){
                for(int j=0;j<count;j++){
                    productShelf.getProdList().remove(0);
                }
            }
        }
    }

    public void displayInventory(){
        List<ProductShelf>productShelfs = inventory.getProdShelfList();
        for(int i=0;i<productShelfs.size();i++){
            ProductShelf prod = productShelfs.get(i);
            System.out.println("Product Name : "+ prod.getCategoryName()+" Product count : "+prod.getProdList().size()+" Price: "+prod.getPrice());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    

   
}
