package DesignTaskManagementSystem;


import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String name;
    
    public User(String email, String name) {
        this.email = email;
        this.name = name;

        this.id = UUID.randomUUID().toString() ;
    }

    // public Task createTask(String title, String description, Long dueDate, Priority priority, TaskType type, User assignee){
    //     User assigneePerson = assignee == null ? this : assignee;
    //     Task newTask = new Task(title,description, dueDate, priority, type, this, assigneePerson) ;
    //     this.createdTasks.put(newTask.getId(), newTask) ;

    //     return newTask;
    // }

    // public Task assignTask(Task task, User newAssignee){
        
    //     if(task.getAssignee().getEmail().equals(this.email)){
    //         this.assignedTasks.remove(task.getId()) ;
    //     }

    //     newAssignee.addInAssignedTasks(task) ;

    //     return task;
    // }

    // public void addInAssignedTasks(Task task){
    //     this.assignedTasks.put(task.getId(), task) ;
    // }




    /*  Getters / Setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
