package DesignPubSubSystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/*
 * Message Queue (BlockingQueue<String> messageQueue): Decouples message publishing and delivery.
 */
public class Topic {
    private String name;
    private final CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    
    public Topic(String name) {
        this.name = name;
        startMessageDispatcher();
    }

    public String getName() { return name; }


    public void addSubscriber(Subscriber subscriber){
        this.subscribers.add(subscriber) ;
    }

    public void publish(Message message){
        messageQueue.offer(message);
    }

    private void startMessageDispatcher(){
        executorService.execute(()->{
            try {
                while (true) {
                    Message message = messageQueue.take(); // Blocking call
                    for (Subscriber subscriber : subscribers) {
                        subscriber.receive(message.getContent());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

}
