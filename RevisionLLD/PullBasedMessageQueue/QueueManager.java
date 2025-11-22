package PullBasedMessageQueue;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class QueueManager {
    private final Map<String, InMemoryQueue<?>> queues = new HashMap<>();

    public <T> InMemoryQueue<T> createQueue(String name) {
        InMemoryQueue<T> q = new InMemoryQueue<>();
        queues.put(name, q);
        return q;
    }

    public <T> Consumer<T> createConsumer(String queueName, String consumerId) {
        InMemoryQueue<T> q = (InMemoryQueue<T>) queues.get(queueName);
        return new Consumer<>(consumerId, q);
    }

    public <T> Publisher<T> createPublisher(String queueName) {
        InMemoryQueue<T> q = (InMemoryQueue<T>) queues.get(queueName);
        return new Publisher<>(q);
    }
}
