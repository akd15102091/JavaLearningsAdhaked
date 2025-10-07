package DesignPubSubSystem;

public class DriverMain {
    public static void main(String[] args) {
        MessageBroker broker = new MessageBroker();

        // creating topics
        broker.createTopic("Topic1");
        broker.createTopic("Topic2");
        broker.createTopic("Topic3");

        // creating subscribers
        Subscriber subscriber1 = new ConcreteSubscriber("Subscriber1") ;
        Subscriber subscriber2 = new ConcreteSubscriber("Subscriber2") ;
        Subscriber subscriber3 = new ConcreteSubscriber("Subscriber3") ;

        // subscriber will subscribe topic
        broker.subscribe("Topic1", subscriber1);
        broker.subscribe("Topic1", subscriber2);
        broker.subscribe("Topic2", subscriber2);
        broker.subscribe("Topic3", subscriber3);


        // creating publisher
        Publisher publisher1 = new Publisher(broker) ;
        Publisher publisher2 = new Publisher(broker) ;


        // Publishing the message
        publisher1.publishMessage("Topic1", "Published by publisher1 for topic1");
        publisher1.publishMessage("Topic2", "Published by publisher1 for topic1");
        publisher2.publishMessage("Topic1", "Published by publisher2 for topic2");
        publisher2.publishMessage("Topic3", "Published by publisher2 for topic3");

        
    }
}
