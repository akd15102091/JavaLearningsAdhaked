package Threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {
    
    int isWriting =0;
    // int readers =0;
    int waitingWriters =0;
    ReentrantLock lock = new ReentrantLock();
    AtomicInteger readers = new AtomicInteger(0);

    Condition canRead = lock.newCondition();
    Condition canWrite = lock.newCondition();


    public void startRead(int id){
        // lock.lock();
        try{
            while(isWriting==1){
                System.out.println("Reader "+id+" is waiting ");
                canRead.await();
            }
            readers.incrementAndGet();
            System.out.println("Reader "+id+" is started reading ");

        }
        catch(Exception e){
            //handle exception
        }
        finally{
            // lock.unlock();
        }
    }

    public void stopRead(int id){
        // lock.lock();
        readers.decrementAndGet();
        System.out.println("reader "+id+" finished reading");
        if(readers.get()==0 ){
            System.out.println("signaling from stopread");
            canWrite.signalAll();
        }

        // lock.unlock();
    }

    public void startWriting(int id){
        try{
            lock.lock();
            waitingWriters++;
            while(isWriting==1 || readers.get()>0){
                System.out.println("writer "+id+" is waiting");
                canWrite.await();
            }
            System.out.println("writer "+id+" has started writing");
            waitingWriters--;
            isWriting = 1;
        }
        catch(Exception e){

        }finally{
            lock.unlock();
        }   
    }

    public void stopWriting(int id){
        lock.lock();
        isWriting=0;
        System.out.println("writer "+id+" has stopped writing");
        // if(readers>0 || waitingWriters>0){
            canRead.signalAll();
            canWrite.signalAll();
        // }

        lock.unlock();
    }

    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();

        Runnable readerTask = () -> {
            int id = (int) (Math.random() * 100);
            try {
                readerWriter.startRead(id);
                Thread.sleep(500); // Simulate reading time
                readerWriter.stopRead(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Creating writer threads
        Runnable writerTask = () -> {
            int id = (int) (Math.random() * 100);
            try {
                readerWriter.startWriting(id);
                Thread.sleep(1000); // Simulate writing time
                readerWriter.stopWriting(id);
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
