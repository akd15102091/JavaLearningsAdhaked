package DesignACoffeeVendingMachine;

import java.util.HashMap;
import java.util.Map;

// Singleton Inventory Manager
public class InventoryManager {
    private static InventoryManager instance;
    private final Map<IngredientType, Integer> ingredients = new HashMap<>() ;

    private InventoryManager(){
        ingredients.put(IngredientType.WATER, 1000);
        ingredients.put(IngredientType.MILK, 500);
        ingredients.put(IngredientType.COFFEE, 300);
        ingredients.put(IngredientType.SUGAR, 200);
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public synchronized boolean useIngredient(IngredientType type, int amount){
        if(ingredients.getOrDefault(type, 0) >= amount){
            ingredients.put(type, ingredients.get(type) - amount) ;
            return true;
        }
        return false;
    }

    public synchronized void refillInventory(IngredientType type, int amount){
        this.ingredients.put(type, this.ingredients.getOrDefault(type, 0) + amount) ;
    }

    public synchronized int getQuantity(IngredientType type){
        return ingredients.getOrDefault(type, 0) ;
    }


}
