public class MinuteBasedStrategy implements PricingStrategy{
    @Override
    public double price(Ticket ticket) {
        return getMinutes(ticket)*5 ;
    }

    public double getMinutes(Ticket ticket){
        long currTime = System.currentTimeMillis() + (1*60*60*1000) ;
        return Math.ceil((currTime-ticket.getEntryTime())/60000) ;
    }
}
