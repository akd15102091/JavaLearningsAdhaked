package DesignTaskManagementSystem;

import java.util.Date;
import java.util.List;

import DesignTaskManagementSystem.Filtering.FilterStrategy;
import DesignTaskManagementSystem.Filtering.PriorityBasedFilterStrategy;

public class DriverMain {
    public static void main(String[] args) {
        TaskManager taskManager = TaskManager.getInstance();

        // Create users
        User user1 = new User("john@gmail.com", "John");
        User user2 = new User("jane@example.com", "Jane Smith");

        // Create tasks
        Task task1 = new Task("Task1", "Description1", new Date(), Priority.LOW, TaskType.STORY, user2);
        Task task2 = new Task("Task2", "Description2", new Date(), Priority.MID, TaskType.FEATURE, user1);
        Task task3 = new Task("Task3", "Description3", new Date(), Priority.HIGH, TaskType.BUG, user2);

        // Add tasks to the task manager
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        // Update a task
        task2.setDescription("Updated description");
        taskManager.updateTask(task2);
        task2.printTaskDetail();

        // Search tasks
        List<Task> searchResults = taskManager.searchTasks("Task");
        System.out.println("Search Results:");
        for (Task task : searchResults) {
            System.out.println(task.getTitle());
        }

        // Filter tasks
        List<Task> filteredTasks = taskManager.filterTasks(Status.OPENED, new Date(0), new Date(), Priority.HIGH);
        System.out.println("Filtered Tasks:");
        for (Task task : filteredTasks) {
            System.out.println(task.getTitle());
        }

        System.out.println("High Priority Tasks:");
        FilterStrategy filterStrategy = new PriorityBasedFilterStrategy(Priority.HIGH) ;
        List<Task> highPriorityTasks = filterStrategy.filterTasks(filteredTasks);
        for (Task task : highPriorityTasks) {
            System.out.println(task.getTitle());
        }


    }
    
}
