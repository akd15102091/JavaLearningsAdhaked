package InMemoryTaskSchedular;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.schedule(() -> System.out.println("Run at specific time: " + Instant.now()),
                Instant.now().plusSeconds(3));

        scheduler.scheduleAtFixedInterval(
                () -> System.out.println("Recurring every 2 sec → " + Instant.now()),
                2
        );

        // Keep running
        try { Thread.sleep(15000); } catch (Exception ignored) {}
        scheduler.shutdown();
    }
}
