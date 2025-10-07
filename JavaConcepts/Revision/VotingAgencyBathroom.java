package Revision;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
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

class Person extends Thread{
    String name = "";
    String party = "";
    Bathroom bathroom;

    Person(String name, String party, Bathroom bathroom){
        this.name = name;
        this.party = party;
        this.bathroom = bathroom;
    }

    public int getSleepTime(){
        return this.name.length()*1000 ;
    }

    @Override
    public void run(){
        try {
            this.bathroom.enter(this); // passing person object itself 
            Thread.sleep(getSleepTime()) ;
            this.bathroom.exit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

class Bathroom {
    String currentGroup = "N"; // N - No one, R, D
    int capacity = 3;
    int currentCount = 0;
    Queue<Person> waitingQueue = new LinkedList<>() ;

    Lock lock = new ReentrantLock() ;
    Condition condition = lock.newCondition() ;

    public boolean canEnter(Person person){
        boolean enterVal =  currentCount<capacity && (person.party.equals(currentGroup) || currentGroup.equals("N"));
        return enterVal;
    }

    public void enter(Person person){
        try {
            lock.lock();
            waitingQueue.add(person) ;
            while(!canEnter(person)) {
                System.out.println("Waiting: "+person.name+", "+person.party);
                condition.await();
            }
            waitingQueue.remove();
            currentCount++;
            System.out.println("Entered: "+person.name+", "+person.party +",currentCount: "+currentCount);
            currentGroup = person.party ;
            // break;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void exit(Person person){
        try {
            lock.lock();
            currentCount--;
            if(currentCount==0){
                currentGroup = "N" ;
            }       
            condition.signalAll();;         

            System.out.println("Exited: "+person.name+", "+person.party +", currentCount: "+currentCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

}

public class VotingAgencyBathroom {

    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom() ;
    
        Person[] people = {
            new Person("Alice", "R", bathroom),
            new Person("Bob", "R", bathroom),
            new Person("Alex", "D", bathroom),
            new Person("Glenn", "D", bathroom),
            new Person("William", "R", bathroom),
            new Person("Thomas", "D", bathroom),
            new Person("Tom", "R", bathroom),
            new Person("Cruise", "D", bathroom)
        };

        for(Person p : people){
            p.start();
        }
    }
}







