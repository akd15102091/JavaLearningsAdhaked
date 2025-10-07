package DesignJobSchedulerLLD;

import java.time.Instant;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JobSchedulerService scheduler = new JobSchedulerService();
        scheduler.start();

        // Create a one-time job
        Task task1 = new Task("task1", () -> System.out.println("One-time task at " + Instant.now()));
        String jobId1 = scheduler.createOneTimeJob("user1", task1, Instant.now().plusSeconds(3));

        
        // Create a recurring job
        Task task2 = new Task("task2", () -> System.out.println("Recurring task at " + Instant.now()));
        String jobId2 = scheduler.createCronJob("user1", task2, "*/5s");

        // Let scheduler run for a while
        Thread.sleep(30000);

        System.out.println("Executions for job1: " + scheduler.getExecutions(jobId1).size());
        System.out.println("Executions for job2: " + scheduler.getExecutions(jobId2).size());
    }
}
