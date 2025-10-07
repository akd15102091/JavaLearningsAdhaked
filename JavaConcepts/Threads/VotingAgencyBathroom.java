package Threads;


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


//  import java.util.LinkedList;
//  import java.util.Queue;
//  import java.util.concurrent.Semaphore;
 
//  class Bathroom {
//      private static final int MAX_CAPACITY = 3;
//      private Semaphore semaphore = new Semaphore(MAX_CAPACITY, true);
//      private Queue<Person> waitingQueue = new LinkedList<>();
//      private String currentGroup = ""; // "D" or "R" or empty
     
//      public synchronized void enterBathroom(Person person) throws InterruptedException {
//          waitingQueue.add(person);
         
//          while (true) {
//              if (canEnter(person)) {
//                  waitingQueue.remove(person);
//                 //  semaphore.acquire();
//                  currentGroup = person.party;
//                  System.out.println(person.name + " (" + person.party + ") entered. Occupied: " + (MAX_CAPACITY - semaphore.availablePermits()));
//                  break;
//              } else {
//                  wait(); // Wait if not eligible to enter
//              }
//          }
//      }
     
//      public synchronized void exitBathroom(Person person) {
//         //  semaphore.release();
//          System.out.println(person.name + " (" + person.party + ") exited. Remaining: " + (MAX_CAPACITY - semaphore.availablePermits()));
         
//         //  if (semaphore.availablePermits() == MAX_CAPACITY) {
//         //      currentGroup = ""; // Reset if bathroom is empty
//         //  }
         
//          notifyAll(); // Notify waiting people
//      }
     
//      private boolean canEnter(Person person) {
//         return true;
//         //  return waitingQueue.peek() == person && (currentGroup.isEmpty() || currentGroup.equals(person.party)) ;
//      }
//  }
 
//  class Person extends Thread {
//      String name;
//      String party;
//      Bathroom bathroom;
     
//     public Person(String name, String party, Bathroom bathroom) {
//         this.name = name;
//         this.party = party;
//         this.bathroom = bathroom;
//     }
     
//      private int f(String name) { 
//          return name.length() * 1000; // Example function based on name length
//      }
     
//      @Override
//      public void run() {
//          try {
//              bathroom.enterBathroom(this);
//              Thread.sleep(f(name)); // Simulate bathroom usage time
//              bathroom.exitBathroom(this);
//          } catch (InterruptedException e) {
//              e.printStackTrace();
//          }
//      }
//  }
 
//  public class VotingAgencyBathroom {
//      public static void main(String[] args) {
//          Bathroom bathroom = new Bathroom();
         
//         //  Person[] people = {
//         //      new Person("Alice", "D", bathroom),
//         //      new Person("Bob", "R", bathroom),
//         //      new Person("Charlie", "D", bathroom),
//         //      new Person("Dave", "D", bathroom),
//         //      new Person("Eve", "R", bathroom),
//         //      new Person("Frank", "R", bathroom)
//         //  };

//         Person[] people2 = {
//             new Person("Alice1", "D", bathroom),
//             new Person("Alice2", "R", bathroom),
//             new Person("Alice3", "R", bathroom),
//             new Person("Alice4", "D", bathroom),
//             new Person("Alice5", "R", bathroom),
//             new Person("Alice6", "D", bathroom),
//             new Person("Alice7", "D", bathroom)
//         };
         
//          for (Person p : people2) {
//              p.start();
//          }
//      }
//  }
 










 /* ---- ANOTHER SOLUTION  */
 
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bathroom {
    private static final int MAX_CAPACITY = 3;
    private int currentCount = 0;
    private char currentGroup = 'N'; // 'D' for Democrat, 'R' for Republican, 'N' for None
    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();
    private final Queue<Person> waitingQueue = new LinkedList<>();

    public void enter(Person person) throws InterruptedException {
        lock.lock();
        try {
            waitingQueue.add(person);
            while (!canEnter(person)) {
                condition.await();
            }
            waitingQueue.remove(person);
            currentCount++;
            currentGroup = person.party;
            System.out.println(person.name + " (" + person.party + ") entered the bathroom.");
        } finally {
            lock.unlock();
        }
    }

    public void exit(Person person) {
        lock.lock();
        try {
            currentCount--;
            System.out.println(person.name + " (" + person.party + ") left the bathroom.");
            if (currentCount == 0) {
                currentGroup = 'N'; // Reset group if no one is left
            }
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean canEnter(Person person) {
        return (currentCount < MAX_CAPACITY && 
                (currentGroup == 'N' || currentGroup == person.party) && waitingQueue.peek()==person);
    }
}

class Person extends Thread {
    String name;
    char party;
    Bathroom bathroom;

    public Person(String name, char party, Bathroom bathroom) {
        this.name = name;
        this.party = party;
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        try {
            bathroom.enter(this);
            Thread.sleep(f(name)); // Simulating bathroom use time
            bathroom.exit(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int f(String name) {
        return (name.length() * 1000) % 5000 + 1000; // Some function based on name
    }
}

public class VotingAgencyBathroom {
    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom();
        Person[] people = {
            new Person("Alice", 'D', bathroom),
            new Person("Bob", 'R', bathroom),
            new Person("Charlie", 'D', bathroom),
            new Person("David", 'D', bathroom),
            new Person("Eve", 'R', bathroom),
            new Person("Frank", 'R', bathroom),
            new Person("Grace", 'D', bathroom)
        };
        Person[] people2 = {
            new Person("Alice1", 'D', bathroom),
            new Person("Alice2", 'R', bathroom),
            new Person("Alice11", 'R', bathroom),
            new Person("Alice12", 'R', bathroom),
            new Person("Alice3", 'D', bathroom),
            new Person("Alice4", 'D', bathroom),
            new Person("Alice5", 'R', bathroom),
            new Person("Alice6", 'R', bathroom),
            new Person("Alice7", 'D', bathroom)
        };

        for (Person person : people2) {
            person.start();
        }
    }
}


