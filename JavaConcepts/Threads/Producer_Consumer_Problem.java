package Threads;

/*
    In computing, the producer-consumer problem (also known as the bounded-buffer problem) is a classic example of 
    a multi-process synchronization problem. The problem describes two processes, the producer and the consumer, 
    which share a common, fixed-size buffer used as a queue. 

    The producer’s job is to generate data, put it into the buffer, and start again.
    At the same time, the consumer is consuming the data (i.e. removing it from the buffer), one piece at a time.

    => Problem:
        To make sure that the producer won’t try to add data into the buffer if it’s full and that the consumer 
        won’t try to remove data from an empty buffer.
*/

import java.util.LinkedList;


public class Producer_Consumer_Problem{
    public static void main(String[] args) throws InterruptedException {
        PC pc = new PC();

        Thread thread1 = new Thread(new Runnable() {
            public void run(){
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }      
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run(){
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }      
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public static class PC{
        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;

        public void produce() throws InterruptedException{
            int value = 0;

            while(true){
                synchronized(this){
                    while(list.size()==capacity){
                        wait();
                    }
    
                    System.out.println("Produced item : "+value);
                    list.add(value++) ;
                    notify();
    
                    Thread.sleep(1000);
                }
            }
        }

        public void consume() throws InterruptedException{
            while(true){
                synchronized(this){
                    while(list.size()==0){
                        wait();
                    }
    
                    System.out.println("consumed item : "+list.remove());
                    notify();
    
                    Thread.sleep(1000);
                }
            }
        }
    }
}

