package DesignACoffeeVendingMachine;


class Espresso extends Beverage{
    public Espresso(){
        this.name = "Espresso";
        recipe.put(IngredientType.WATER, 50);
        recipe.put(IngredientType.COFFEE, 30);
    }
}

class Cappuccino extends Beverage {
    public Cappuccino() {
        this.name = "Cappuccino";
        recipe.put(IngredientType.WATER, 50);
        recipe.put(IngredientType.MILK, 50);
        recipe.put(IngredientType.COFFEE, 30);
    }
}

public class BeverageFactory {
    public static Beverage getBeverage(String type) {
        switch (type.toLowerCase()) {
            case "espresso":
                return new Espresso();
            case "cappuccino":
                return new Cappuccino();
            default:
                throw new IllegalArgumentException("Unknown beverage: " + type);
        }
    }
}
