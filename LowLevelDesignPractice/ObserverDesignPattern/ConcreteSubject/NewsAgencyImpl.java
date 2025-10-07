package ConcreteSubject;

import java.util.ArrayList;
import java.util.List;

import ObserverInterface.Subscriber;
import SubjectInterface.NewsAgency;

public class NewsAgencyImpl implements NewsAgency{
    private String latestNews;
    private List<Subscriber> subscribers = new ArrayList<>() ;

    @Override
    public void registerObserver(Subscriber subscriber) {
        this.subscribers.add(subscriber) ;
    }

    @Override
    public void removeObserver(Subscriber subscriber) {
        this.subscribers.remove(subscriber) ;
    }

    @Override
    public void notifyObservers() {
        for(Subscriber subscriber : this.subscribers){
            subscriber.update(this.latestNews);
        }
    }

    public void setNews(String latestNews){
        this.latestNews = latestNews;
        this.notifyObservers();
    }
    
}
