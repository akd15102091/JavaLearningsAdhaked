import java.util.List;

public class Inventory {
    List<ItemShelf>itemShelfList;

    public List<ItemShelf> getItemShelfList() {
        return itemShelfList;
    }

    public void setItemShelfList(List<ItemShelf> itemShelfList) {
        this.itemShelfList = itemShelfList;
    }
    public void displayInventory(){
        for(int i=0;i<itemShelfList.size();i++){
            System.out.println("Code: "+itemShelfList.get(i).getCode()+"Item: "+itemShelfList.get(i).getItem().toString()+ "isSoldOut: "+itemShelfList.get(i).isSoldOut());
        }
    }

    public ItemShelf getItemByCode(int code){
        for(int i=0;i<itemShelfList.size();i++){
            if(itemShelfList.get(i).getCode() == code){
                return itemShelfList.get(i);
            }
        }
        return null;
    }
}
