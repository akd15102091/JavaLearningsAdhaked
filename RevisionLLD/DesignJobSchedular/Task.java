package DesignJobSchedular;

public class Task {
    public String taskId;
    public Runnable action;

    public Task(String taskId, Runnable action){
        this.taskId = taskId;
        this.action = action;
    }

    public void execute(){
        try{
            this.action.run();
            System.out.println("Task with id "+taskId+" has completed.");
        } catch(Exception e){
            System.out.println("Task with id "+taskId+" has failed. Caught in Try Catch block.");
        }
    }
}
