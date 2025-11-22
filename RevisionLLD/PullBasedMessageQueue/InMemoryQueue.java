package PullBasedMessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryQueue<T> {

    private final List<Message<T>> messages = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    // Publisher adds new message
    public void publish(Message<T> msg) {
        lock.lock();
        try {
            messages.add(msg);
            notEmpty.signalAll(); // wake all consumers
        } finally {
            lock.unlock();
        }
    }

    // Get message at offset; returns null if out of range
    public Message<T> getAt(int offset) {
        lock.lock();
        try {
            if (offset < messages.size()) {
                return messages.get(offset);
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return messages.size();
        } finally {
            lock.unlock();
        }
    }

    public Condition getCondition() {
        return notEmpty;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}