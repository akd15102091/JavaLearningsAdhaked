package Revision;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {
    private final Lock[] forks;
    private final Lock globalLock = new ReentrantLock(); // Ensures fairness

    public DiningPhilosophers() {
        forks = new Lock[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        
        int leftFork = philosopher;
        int rightFork = (philosopher + 1) % 5;

        // Prevent deadlocks by acquiring the global lock before picking up forks
        globalLock.lock();
        try {
            forks[leftFork].lock();
            pickLeftFork.run();

            forks[rightFork].lock();
            pickRightFork.run();
        } finally {
            globalLock.unlock();
        }

        // Eating
        eat.run();
        Thread.sleep(1000); // Simulate eating time

        // Put down forks
        forks[leftFork].unlock();
        putLeftFork.run();

        forks[rightFork].unlock();
        putRightFork.run();
    }
}

class Philosopher extends Thread {
    private final int id;
    private final DiningPhilosophers diningPhilosophers;

    public Philosopher(int id, DiningPhilosophers diningPhilosophers) {
        this.id = id;
        this.diningPhilosophers = diningPhilosophers;
    }

    @Override
    public void run() {
        try {
            while (true) { // Philosophers think and eat indefinitely
                think();
                diningPhilosophers.wantsToEat(id, 
                    () -> System.out.println("Philosopher " + id + " picked up left fork"),
                    () -> System.out.println("Philosopher " + id + " picked up right fork"),
                    () -> System.out.println("Philosopher " + id + " is eating"),
                    () -> System.out.println("Philosopher " + id + " put down left fork"),
                    () -> System.out.println("Philosopher " + id + " put down right fork")
                );
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking...");
        Thread.sleep(500 + (int) (Math.random() * 500)); // Simulate thinking time
    }
}

public class DiningPhilosophersMain {
    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, diningPhilosophers);
            philosophers[i].start();
        }
    }
}

