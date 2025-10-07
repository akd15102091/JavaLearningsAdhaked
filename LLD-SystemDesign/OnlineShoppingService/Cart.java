package OnlineShoppingService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private final Map<Product, Integer> items;

    public Cart() {
        items = new ConcurrentHashMap<>();
    }

    public void add(Product product, int quantity) {
        items.merge(product, quantity, Integer::sum);
    }

    public Map<Product, Integer> getItems() {
        return new HashMap<Product, Integer>(items);
    }

    public void clear() {
        items.clear();
    }
}
