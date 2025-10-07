public class HourlyBasedStrategy implements PricingStrategy{

    @Override
    public double price(Ticket ticket) {
        return getHours(ticket)*100 ;
    }

    public double getHours(Ticket ticket){
        long currTime = System.currentTimeMillis() + (2*60*60*1000) ;
        return Math.ceil((currTime-ticket.getEntryTime())/3600000) ;
    }
    
}
