
import ConcreteObservers.EmailSubscriber;
import ConcreteObservers.SmsSubscriber;
import ConcreteSubject.NewsAgencyImpl;
import ObserverInterface.Subscriber;

public class Client {
    public static void main(String[] args) {
        NewsAgencyImpl newsAgency = new NewsAgencyImpl() ;

        //defining subscribers/observers
        Subscriber subscriber1 = new EmailSubscriber("Ashwini") ;
        Subscriber subscriber2 = new EmailSubscriber("AKD") ;
        Subscriber subscriber3 = new SmsSubscriber("9678543140") ;
        Subscriber subscriber4 = new SmsSubscriber("9876543210") ;

        //registering subscribers/observers
        newsAgency.registerObserver(subscriber1);
        newsAgency.registerObserver(subscriber2);
        newsAgency.registerObserver(subscriber3);
        newsAgency.registerObserver(subscriber4);

        // setting news
        newsAgency.setNews("Prime minister is on visit to Russia.") ;
    }
}
