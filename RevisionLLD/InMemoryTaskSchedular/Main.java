package InMemoryTaskSchedular;

import java.time.Instant;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.schedule(() -> System.out.println("Run at specific time: " + Instant.now().atZone(ZoneId.of("Asia/Kolkata"))),
                Instant.now().plusSeconds(3));

        scheduler.scheduleAtFixedInterval(
                () -> System.out.println("Recurring every 2 sec → " + Instant.now().atZone(ZoneId.of("Asia/Kolkata"))),
                2
        );

        // Keep running
        try { Thread.sleep(15000); } catch (Exception ignored) {}
        scheduler.shutdown();


        // Wait for all tasks to finish
        // try {
        //     scheduler.awaitTermination();   // custom method
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        System.out.println("All tasks completed. Main thread ending.");
    }
}
