package DesignJobSchedulerLLD;

/*
 * Encapsulates the actual business logic/work to be executed. Jobs reference a Task.
 */
public class Task {
    private String taskId;
    private Runnable action; // actual logic to run

    public Task(String taskId, Runnable action) {
        this.taskId = taskId;
        this.action = action;
    }

    public String getTaskId() {
        return taskId;
    }

    public void execute() {
        action.run();
    }
}
