package PullBasedMessageQueue;

@SuppressWarnings("unused")
public class Consumer<T> {
    private final String id;
    private int offset = 0; // unique offset per consumer
    private final InMemoryQueue<T> queue;

    public Consumer(String id, InMemoryQueue<T> queue) {
        this.id = id;
        this.queue = queue;
    }

    // BLOCKING poll with TTL handling
    public T poll() throws InterruptedException {

        queue.getLock().lock();
        try {
            while (true) {

                // wait if offset has no new messages yet
                while (offset >= queue.size()) {
                    queue.getCondition().await();
                }

                Message<T> msg = queue.getAt(offset);
                offset++; // move forward

                long now = System.currentTimeMillis();
                if (!msg.isExpired(now)) {
                    return msg.getData();
                }

                // expired → skip and continue loop
            }
        } finally {
            queue.getLock().unlock();
        }
    }
}
