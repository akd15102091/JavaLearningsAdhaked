package Revision;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bathroom {
    private static final int MAX_CAPACITY = 3;
    private int currentCount = 0;  // Number of people inside the bathroom
    private String currentParty = ""; // Tracks whether "D" or "R" are using the bathroom

    private final Lock lock = new ReentrantLock();
    private final Condition democratCondition = lock.newCondition();
    private final Condition republicanCondition = lock.newCondition();

    // Function to determine how long a person stays (based on name length)
    private int f(String name) {
        return (name.length() * 1000) % 5000 + 1000; // Between 1 to 5 seconds
    }

    public void enterBathroom(String name, String party) throws InterruptedException {
        lock.lock();
        try {
            // Wait until the bathroom is empty or the same party is using it
            while (currentCount == MAX_CAPACITY || (!currentParty.isEmpty() && !currentParty.equals(party))) {
                if (party.equals("D")) {
                    democratCondition.await();
                } else {
                    republicanCondition.await();
                }
            }

            // Enter the bathroom
            currentCount++;
            currentParty = party;
            System.out.println(name + " (" + party + ") entered. Inside: " + currentCount + " [" + currentParty + "]");

        } finally {
            lock.unlock();
        }

        // Simulate time inside the bathroom
        Thread.sleep(f(name));

        exitBathroom(name, party);
    }

    public void exitBathroom(String name, String party) {
        lock.lock();
        try {
            currentCount--;
            System.out.println(name + " (" + party + ") exited. Inside: " + currentCount + " [" + currentParty + "]");

            if (currentCount == 0) {
                currentParty = ""; // Reset when bathroom becomes empty
            }

            // Signal waiting threads
            if (currentParty.equals("D")) {
                democratCondition.signalAll();
            } else {
                republicanCondition.signalAll();
            }

        } finally {
            lock.unlock();
        }
    }
}

class Person extends Thread {
    private final String name;
    private final String party;
    private final Bathroom bathroom;

    public Person(String name, String party, Bathroom bathroom) {
        this.name = name;
        this.party = party;
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        try {
            bathroom.enterBathroom(name, party);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Voting {
    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom();

        // Simulating people using the bathroom
        Person[] people = {
            new Person("Alice", "D", bathroom),
            new Person("Bob", "D", bathroom),
            new Person("Charlie", "R", bathroom),
            new Person("David", "R", bathroom),
            new Person("Eve", "D", bathroom),
            new Person("Frank", "R", bathroom),
            new Person("Grace", "D", bathroom),
            new Person("Hank", "R", bathroom),
            new Person("Ivy", "D", bathroom),
            new Person("Jack", "R", bathroom)
        };

        for (Person p : people) {
            p.start();
        }
    }
}

