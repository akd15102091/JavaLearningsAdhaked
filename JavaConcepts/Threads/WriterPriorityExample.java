package Threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PriorityReadWriteLock {
    private int readers = 0; // Number of active readers
    private int waitingWriters = 0; // Writers waiting to acquire the lock
    private boolean isWriting = false; // If a writer is active

    private final Lock lock = new ReentrantLock();
    private final Condition readCondition = lock.newCondition(); // Condition for readers
    private final Condition writeCondition = lock.newCondition(); // Condition for writers

    // Reader method
    public void startRead(int readerId) throws InterruptedException {
        lock.lock();
        try {
            // Writers get priority: New readers must wait if a writer is waiting
            while (isWriting ) {
                System.out.println("Reader " + readerId + " waiting due to writer priority...");
                readCondition.await();
            }
            readers++; // Increment active readers
            System.out.println("Reader " + readerId + " is reading...");
        } finally {
            lock.unlock();
        }
    }

    public void stopRead(int readerId) {
        lock.lock();
        try {
            readers--; // Decrement active readers
            System.out.println("Reader " + readerId + " finished reading.");
            if (readers == 0) {
                writeCondition.signal(); // Notify writers if no more readers
            }
        } finally {
            lock.unlock();
        }
    }

    // Writer method
    public void startWrite(int writerId) throws InterruptedException {
        lock.lock();
        try {
            waitingWriters++; // Mark a writer is waiting
            while (isWriting || readers > 0) { // Wait if another writer or readers are active
                System.out.println("Writer " + writerId + " waiting...");
                writeCondition.await();
            }
            waitingWriters--; // Remove from waiting count
            isWriting = true; // Set writing flag
            System.out.println("Writer " + writerId + " is writing...");
        } finally {
            lock.unlock();
        }
    }

    public void stopWrite(int writerId) {
        lock.lock();
        try {
            isWriting = false; // Writing is done
            System.out.println("Writer " + writerId + " finished writing.");
            if (waitingWriters > 0) {
                writeCondition.signal(); // Give priority to another writer
            } else {
                readCondition.signalAll(); // If no writers, allow readers
            }
        } finally {
            lock.unlock();
        }
    }
}

public class WriterPriorityExample {
    public static void main(String[] args) {
        PriorityReadWriteLock rwLock = new PriorityReadWriteLock();

        // Creating reader threads
        Runnable readerTask = () -> {
            int id = (int) (Math.random() * 100);
            try {
                rwLock.startRead(id);
                Thread.sleep(500); // Simulate reading time
                rwLock.stopRead(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Creating writer threads
        Runnable writerTask = () -> {
            int id = (int) (Math.random() * 100);
            try {
                rwLock.startWrite(id);
                Thread.sleep(1000); // Simulate writing time
                rwLock.stopWrite(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Starting multiple readers and writers
        Thread r1 = new Thread(readerTask);
        Thread r2 = new Thread(readerTask);
        Thread w1 = new Thread(writerTask);
        Thread r3 = new Thread(readerTask);
        Thread w2 = new Thread(writerTask);
        
        r1.start();
        r2.start();
        w1.start();
        r3.start();
        w2.start();
    }
}
