import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<ProductShelf,Integer> catIdVsCnt =new HashMap<>(); // catogoryID vs count
    double totalCost=0;

    public void addItem(ProductShelf productShelf, int count){
        catIdVsCnt.put(productShelf, count) ;
        this.totalCost += (productShelf.getPrice()*count) ;
    }

    public double getTotalCost(){
        return this.totalCost;
    }

}
