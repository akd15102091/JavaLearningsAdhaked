package Revision_Multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * The Reader-Writer Problem in multithreading occurs when multiple threads access shared data concurrently. The problem arises due to the need for synchronization between multiple reader threads (which only read data) and writer threads (which modify the data).

    # Problem Explanation
        - Multiple Readers can Read Simultaneously: Since reading does not modify the data, multiple reader threads should be allowed to read the shared resource concurrently.
        - Writers must have Exclusive Access: If a writer thread modifies the data, no other thread (reader or writer) should access it at the same time to prevent data inconsistency.

    # Types of Reader-Writer Problems
        1) First Readers-Writers Problem (Readers' Preference):
            - Multiple readers can read concurrently.
            - A writer must wait until all readers are done before writing.
            - Issue: A writer may starve if there are always readers present.
            
        2) Second Readers-Writers Problem (Writers' Preference)
            - A writer gets priority over readers.
            - Once a writer is waiting, no new readers can start reading until the writer finishes.
            - Issue: Readers may starve if writers keep coming.
        
        3) Third Readers-Writers Problem (Fairness Approach - Using Queues)
            - Readers and writers are treated fairly in the order they arrive.
            - Prevents both reader and writer starvation.
            - Typically implemented using a queue or semaphores.
 */

@SuppressWarnings("unused")
public class ReaderWriter {
    private int readers = 0;
    private int waitingWriters = 0;
    private boolean isWriting = false;
    private ReentrantLock lock = new ReentrantLock() ;
    private Condition canWrite = lock.newCondition() ;
    private Condition canRead = lock.newCondition() ;

    public void startRead(int id){
        lock.lock();
        try {
            while (isWriting) {
                System.out.println("Reader "+id+" is waiting.....");
                canRead.await();
            }
            System.out.println("Reader "+id+" is started reading.");
            readers++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void stopRead(int id){
        lock.lock();
        try {
            System.out.println("Reader "+id+" is stopped reading.");
            readers--;
            if(readers==0){
                canWrite.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void startWrite(int id){
        lock.lock();
        try {
            waitingWriters++;
            while (isWriting || readers>0) {
                System.out.println("Writer "+id+" is waiting.....");
                canWrite.await();
            }
            waitingWriters--;
            isWriting = true;
            System.out.println("Writer "+id+" is started writing.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void stopWrite(int id){
        lock.lock();
        try {
            isWriting = false;
            System.out.println("Writer "+id+" is stopped writing.");
            canRead.signalAll();
            canWrite.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReaderWriter rw = new ReaderWriter();

        Runnable readerTask = (()-> {
            int id = (int)(Math.random()*100) ;
            try {
                rw.startRead(id);
                Thread.sleep(500);
                rw.stopRead(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Runnable writerTask = (()-> {
            int id = (int)(Math.random()*100) + 1000 ;
            try {
                rw.startWrite(id);
                Thread.sleep(1000);
                rw.stopWrite(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread reader1 = new Thread(readerTask) ;
        Thread reader2 = new Thread(readerTask) ;
        Thread reader3 = new Thread(readerTask) ;

        Thread writer1 = new Thread(writerTask);
        Thread writer2 = new Thread(writerTask);

        reader1.start();
        writer1.start();
        reader2.start();
        writer2.start();
        reader3.start();
    }
}
