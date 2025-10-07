public class Item {
    ItemType itemType;
    int price;
    public Item(ItemType itemType, int price) {
        this.itemType = itemType;
        this.price = price;
    }
    public ItemType getItemType() {
        return itemType;
    }
    public int getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Item [itemType=" + itemType + ", price=" + price + "]";
    }

    
}
