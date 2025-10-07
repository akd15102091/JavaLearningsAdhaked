package DesignACoffeeVendingMachine;

import java.util.HashMap;
import java.util.Map;

public abstract class Beverage {
    protected String name="";
    protected Map<IngredientType, Integer> recipe = new HashMap<>();
    
    public String getName() {
        return name;
    }

    public boolean prepare(){
        InventoryManager inventoryManager = InventoryManager.getInstance();

        for(Map.Entry<IngredientType, Integer> entry : recipe.entrySet()){
            IngredientType type = entry.getKey();
            int amount = entry.getValue();
            if(!inventoryManager.useIngredient(type, amount)){
                System.out.println("Not enough " + entry.getKey() + " to prepare " + name);
                return false;
            }
        }

        System.out.println(name + " is ready.");
        return true; 
    }
}
