package PullBasedMessageQueue;

public class Publisher<T> {

    private final InMemoryQueue<T> queue;

    public Publisher(InMemoryQueue<T> queue) {
        this.queue = queue;
    }

    public void publish(T data, Long ttlMillis) {
        queue.publish(new Message<>(data, ttlMillis));
    }
}
