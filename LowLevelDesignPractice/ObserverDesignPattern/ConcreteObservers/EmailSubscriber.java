package ConcreteObservers;

import ObserverInterface.Subscriber;

public class EmailSubscriber implements Subscriber{
    public String name;

    public EmailSubscriber(String name){
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println("Send email to "+this.name + ". News is : "+news);
    }
    
}
