package PullBasedMessageQueue;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        QueueManager qm = new QueueManager();
        InMemoryQueue<String> q = qm.createQueue("orders");

        Publisher<String> p1 = qm.createPublisher("orders");
        Consumer<String> c1 = qm.createConsumer("orders", "C1");

        // producer thread
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                p1.publish("Hello", null);
                Thread.sleep(2000);
                p1.publish("Uber", 3000L);  // TTL 3 sec
                Thread.sleep(10000);
                p1.publish("World", null);

            } catch (Exception e) {}
        }).start();

        // consumer thread
        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Consumed = " + c1.poll());
                }
            } catch (Exception e) {}
        }).start();
    }
}
