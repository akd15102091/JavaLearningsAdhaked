package Threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class SharedQueue {
    private final BlockingQueue<Integer> queue  = new LinkedBlockingQueue<>(3);;

    public SharedQueue(int capacity) {
        
    }

    public void produce() {
        int value = 1;
        while (true) {
            try {                

                queue.put(value); // Blocks if queue is full
                System.out.println("Produced: " + value);

                value++;
                Thread.sleep(100); // Simulating work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void consume() {
        while (true) {
            try {
                Thread.sleep(10000); // Simulating work

                int value = queue.take(); // Blocks if queue is empty
                System.out.println("Consumed: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class Producer implements Runnable {
    private final SharedQueue queue;

    public Producer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.produce();
    }
}

class Consumer implements Runnable {
    private final SharedQueue queue;

    public Consumer(SharedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.consume();
    }
}

public class ProducerConsumerBlockingQueueExample {
    public static void main(String[] args) {
        SharedQueue queue = new SharedQueue(2); // Capacity of 3

        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        producerThread.start();
        consumerThread.start();
    }
}
