import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<ProductShelf> prodShelfList = new ArrayList<>();
    
    public void addProduct(ProductShelf product){
        prodShelfList.add(product);
    }

    public void fill(List<ProductShelf> productCategories){
        prodShelfList.addAll(productCategories);
    }

    public List<ProductShelf> getProdShelfList() {
        return prodShelfList;
    }

    public ProductShelf getProdShelfById(int id) {
        for(int i=0;i<prodShelfList.size();i++){
            if(prodShelfList.get(i).getCategoryId() == id){
                return prodShelfList.get(i) ;
            }
        }
        return null;
    }

    
}
