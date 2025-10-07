package Revision;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
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



public class ReaderWriter {
    int readers = 0;
    Lock lock = new ReentrantLock() ;
    Condition canRead = lock.newCondition() ;
    Condition canWrite = lock.newCondition() ;
    boolean isWriting = false;
    int waitingWriters = 0;

    public void startRead(int id){
        try {
            lock.lock();
            while (isWriting) {
                canRead.await();
            }
            readers++;
            System.out.println("Reading started by " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void stopRead(int id){
        try {
            lock.lock();
            readers--;
            if(readers==0){
                canWrite.signalAll();
            }
            System.out.println("Reading stopped by " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void startWrite(int id){
        try {
            lock.lock();
            waitingWriters++;
            while (isWriting || readers>0) {
                canWrite.await();
            }
            waitingWriters--;
            isWriting=true;
            System.out.println("Writing started by " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void stopWrite(int id){
        try {
            lock.lock();
            isWriting = false;
            // if(isWriting==false){
                canWrite.signalAll() ;
                canRead.signalAll() ;
            // }
            System.out.println("Writing stopped by " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReaderWriter readerWriterObj = new ReaderWriter() ;

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                int id = (int)(Math.random()*100);
                readerWriterObj.startRead(id);
                try {
                    Thread.sleep(1000) ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readerWriterObj.stopRead(id);       
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                int id = (int)(Math.random()*100)+1000;
                readerWriterObj.startWrite(id);
                try {
                    Thread.sleep(4000) ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readerWriterObj.stopWrite(id);       
            }
        };

        Thread t1 = new Thread(readTask) ;
        Thread t2 = new Thread(readTask) ;
        Thread t3 = new Thread(readTask) ;

        Thread t4 = new Thread(writeTask) ;
        Thread t5 = new Thread(writeTask) ;

        t1.start();
        t2.start();
        t3.start();

        t4.start();
        t5.start();
    }
}
