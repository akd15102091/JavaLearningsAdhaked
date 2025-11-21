package TrainPlatformManagementSystem;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create system with 3 platforms
        TrainPlatformService service = new TrainPlatformService(3);

        LocalDateTime t1Arr = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime t1Dep = LocalDateTime.of(2025, 1, 1, 11, 0);

        LocalDateTime t2Arr = LocalDateTime.of(2025, 1, 1, 10, 30);
        LocalDateTime t2Dep = LocalDateTime.of(2025, 1, 1, 12, 0);

        LocalDateTime t3Arr = LocalDateTime.of(2025, 1, 1, 10, 5);
        LocalDateTime t3Dep = LocalDateTime.of(2025, 1, 1, 12, 30);

        // Assign train 101
        int p1 = service.assignTrain("T101", t1Arr, t1Dep);
        System.out.println("Train T101 assigned to Platform " + p1);

        // Assign train 102 (overlaps with T101 on same platform, but will go to next free)
        int p2 = service.assignTrain("T102", t2Arr, t2Dep);
        System.out.println("Train T102 assigned to Platform " + p2);

        // Assign train 103 (starts after T101 departs, may reuse same platform)
        int p3 = service.assignTrain("T103", t3Arr, t3Dep);
        System.out.println("Train T103 assigned to Platform " + p3);

        // Query: Which train is at platform 1 at 10:30?
        LocalDateTime queryTime1 = LocalDateTime.of(2025, 1, 1, 10, 30);
        String trainAtP1 = service.getTrainAtPlatform(1, queryTime1);
        System.out.println("At 10:30, Platform 1 has: " + trainAtP1);

        // Query: Which train is at platform 2 at 10:45?
        LocalDateTime queryTime2 = LocalDateTime.of(2025, 1, 1, 10, 45);
        String trainAtP2 = service.getTrainAtPlatform(2, queryTime2);
        System.out.println("At 10:45, Platform 2 has: " + trainAtP2);

        // Query: On which platform is train T102 at 11:00?
        LocalDateTime queryTime3 = LocalDateTime.of(2025, 1, 1, 11, 00);
        Integer platformOf102 = service.getPlatformOfTrain("T102", queryTime3);
        System.out.println("Train T102 is on platform: " + platformOf102);

        // Query: A time where train isn't on platform
        LocalDateTime queryTime4 = LocalDateTime.of(2025, 1, 1, 9, 59);
        String trainAtP1No = service.getTrainAtPlatform(1, queryTime4);
        System.out.println("At 09:59, Platform 1 has: " + trainAtP1No);

        // Try conflicting train to exhaust platforms
        try {
            service.assignTrain("T200",
                    LocalDateTime.of(2025, 1, 1, 9, 15),
                    LocalDateTime.of(2025, 1, 1, 11, 15)
            );
        } catch (Exception e) {
            System.out.println("Expected conflict: " + e.getMessage());
        }

    }
}

