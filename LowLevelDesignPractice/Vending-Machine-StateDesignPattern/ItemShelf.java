public class ItemShelf {
    Item item;
    int code;
    boolean isSoldOut;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }
    
    public void setSoldOut(boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

}
