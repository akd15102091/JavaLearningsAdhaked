import java.util.UUID;

public class Invoice {
    double totalCost;
    UUID gstNo;

    public Invoice(double totalCost) {
        this.totalCost = totalCost;
        this.gstNo = UUID.randomUUID();
        System.out.println("INVOICE GENERATED FOR THIS ORDER WITH COST - "+totalCost);
    }


}
