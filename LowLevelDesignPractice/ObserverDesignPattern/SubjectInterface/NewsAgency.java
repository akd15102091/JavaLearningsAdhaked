package SubjectInterface;

import ObserverInterface.Subscriber;

public interface NewsAgency {
    void registerObserver(Subscriber subscriber);
    void removeObserver(Subscriber subscriber);
    void notifyObservers() ;
}
