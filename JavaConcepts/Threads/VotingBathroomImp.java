package Threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

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
    String name;
    String party;
    Bathroom bathroom;
    public Person(String name, String party, Bathroom bathroom){
        this.name = name;
        this.party = party;
        this.bathroom = bathroom;
    }

    public int timeTaken(){
        return name.length();
    }

    @Override
    public void run(){
        try {
            bathroom.enter(this);
            Thread.sleep(this.timeTaken()*200) ;
            bathroom.exit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
} 

class Bathroom{
    int capacity =3;
    Queue<Person>waitingQ =new LinkedList<>();
    String currGrp= "";
    Semaphore semaphore = new Semaphore(capacity) ;

    public boolean canEnter(Person p){
        if(waitingQ.peek()==p && ( currGrp.equals("") || currGrp.equals(p.party)) && semaphore.availablePermits() > 0 ){
            return true;
        }
        return false;
    }

    public synchronized void enter(Person p) throws InterruptedException{
        waitingQ.add(p);
        while(true){
            if(canEnter(p)){
                Thread.sleep(1000);
                waitingQ.remove(p);
                semaphore.acquire();
                System.out.println("person entered "+p.name+" "+p.party+" Occupied: " + (capacity - semaphore.availablePermits()));
                currGrp = p.party;
                break;
            }
            else{
                wait() ;
            }
        }
    }

    public synchronized void exit(Person p){
        semaphore.release();
        System.out.println("person exited "+p.name+" "+p.party);

        if (semaphore.availablePermits() == capacity) {
            currGrp = ""; // Reset if bathroom is empty
        }

        notifyAll();
    }
}

public class VotingBathroomImp {
    public static void main(String[] args) {
        
        Bathroom bathroom =new Bathroom();

        Person[] people2 = {
            new Person("Alice1", "D", bathroom),
            new Person("Alice2", "R", bathroom),
            new Person("Alice3", "R", bathroom),
            new Person("Alice4", "D", bathroom),
            new Person("Alice5", "R", bathroom),
            new Person("Alice6", "D", bathroom),
            new Person("Alice7", "D", bathroom)
        };

        for(Person p: people2){
            p.start() ;
        }
    }
}
