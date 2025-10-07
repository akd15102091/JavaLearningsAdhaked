import java.util.ArrayList;
import java.util.List;

public class ProductShelf {
    int categoryId;
    String categoryName;
    List<Product> prodList = new ArrayList<>();
    double price;
    
    public ProductShelf(int categoryId, String categoryName, List<Product> prodList, double price) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.prodList = prodList;
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProdList() {
        return prodList;
    }

    public double getPrice() {
        return price;
    }   

    
}
