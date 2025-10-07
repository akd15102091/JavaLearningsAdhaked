package ConcreteObservers;

import ObserverInterface.Subscriber;

public class SmsSubscriber implements Subscriber{
    public String mobile;
    public SmsSubscriber(String mobile){
        this.mobile = mobile;
    }

    @Override
    public void update(String news) {
        System.out.println("Send sms to "+this.mobile + ". News is : "+news);
    }
    
}
