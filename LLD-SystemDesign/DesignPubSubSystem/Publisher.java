package DesignPubSubSystem;

public class Publisher {
    private final MessageBroker broker;
    
    public Publisher(MessageBroker broker) {
        this.broker = broker;
    }
    
    public void publishMessage(String topic, String message) {
        broker.publish(topic, message);
    }
}
