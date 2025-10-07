public class CostCalculator {
    PricingStrategy pricingStrategy;

    public CostCalculator(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double price(Ticket ticket){
       return pricingStrategy.price(ticket) ;
    }
}
