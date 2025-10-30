package Revision_Multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Problem Statement: Single Bathroom Usage in a Voting Agency
In a voting agency, there is a single bathroom shared by both Democrats (D) and Republicans (R). However, there are strict rules governing its usage:

Rules & Constraints:
    The bathroom can accommodate up to 3 people at a time.
    A person takes f(N) seconds inside the bathroom, where f(N) is a function based on the person’s name and returns a varying time duration.
    No mixed groups are allowed. The bathroom can only contain:
        Only Democrats ((3D), (2D), (1D))
        Only Republicans ((3R), (2R), (1R))
        Empty (())
    If the bathroom is occupied, others must wait in a queue.
    The most eligible person should get access as soon as space is available, while ensuring fairness.

*/

class Bathroom{
    private int currentCapacity = 0;
    private String currentGroup = "N";
    private int MAX_CAPACITY = 3 ;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    Queue<Person> waitingQueue = new LinkedList<>() ;

    /**
     * Fairness Intuition:
        - Order must be maintained between groups (R vs D), but within the same group, any member can enter whenever space is available — ensuring fairness without starvation and allowing same-group batching for better throughput.
     */
    public boolean canEnter(Person p) {
        // Bathroom empty → only the first in queue should enter
        if (currentGroup.equals("N")) {
            return waitingQueue.peek() == p;
        }

        // Bathroom occupied → must be same group, not full, 
        // and person must be at front of queue or all earlier ones are same group
        if (p.getGroup().equals(currentGroup) && currentCapacity < MAX_CAPACITY) {
            // Ensure fairness (no skipping opposite group waiting earlier)
            for (Person person : waitingQueue) {
                if (person == p) break;
                if (!person.getGroup().equals(currentGroup)) return false;
            }
            return true;
        }

        return false;
    }

    public void enter(Person p){
        lock.lock();
        try {
            waitingQueue.add(p);
            while (!canEnter(p)) {
                System.out.println(p.getPName()+", "+p.getGroup()+" - waiting...");
                condition.await();
            }
            waitingQueue.remove();
            System.out.println(p.getPName()+", "+p.getGroup()+" - Entered");
            currentCapacity++;
            currentGroup = p.getGroup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void exit(Person p){
        lock.lock();
        try {   
            currentCapacity--;
            if(currentCapacity==0){
                currentGroup = "N";
            }
            System.out.println(p.getPName()+", "+p.getGroup()+" - Exited");
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Person extends Thread{
    private String name;
    private String group;
    private Bathroom bathroom;

    public Person(String name, String group, Bathroom bathroom){
        this.name = name;
        this.group = group;
        this.bathroom = bathroom;
    }

    public String getPName() {
        return name;
    }

    public String getGroup(){
        return this.group;
    }

    private long getWaitingTime(){
        return name.length()*100l;
    }

    @Override
    public void run(){
        try {
            this.bathroom.enter(this);
            Thread.sleep(getWaitingTime());
            this.bathroom.exit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class VotingAgencyBathroom {
    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom();

        Person[] arr = {
            new Person("Alice", "D", bathroom),
            new Person("Bob", "R", bathroom),
            new Person("John", "R", bathroom),
            new Person("Jack", "D", bathroom),
            new Person("Peterson", "D", bathroom),
            new Person("Anderson", "R", bathroom),
            new Person("Rishabh", "D", bathroom),
            new Person("Shreyash", "R", bathroom)
        } ;

        for(Person p : arr){
            p.start();
        }   
    }
}
