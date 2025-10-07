package DesignPubSubSystem;

public class ConcreteSubscriber implements Subscriber{
    private String name;

    public ConcreteSubscriber(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void receive(String message) {
        System.out.println("Subscriber "+name+" received a message : "+message);
    }
    
    

}
