package DesignPubSubSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Message Broker to manage topics and dispatch messages
public class MessageBroker {
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    public void createTopic(String topicName){
        topics.putIfAbsent(topicName, new Topic(topicName));
    }

    public void subscribe(String topicName, Subscriber subscriber){
        topics.get(topicName).addSubscriber(subscriber);
    }

    public void publish(String topicName, String message) {
        Topic topic = topics.get(topicName);
        if (topic != null) {
            topic.publish(new Message(message));
        }
        else{
            System.out.println("Can't publish the message, as there is no such topic exist");
        }
    }
}
