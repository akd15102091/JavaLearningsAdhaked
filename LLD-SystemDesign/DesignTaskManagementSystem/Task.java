package DesignTaskManagementSystem;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private Priority priority;
    private TaskType type; // bug, story, epic, feature
    private Status status;
    private long createdTime;
    private User createdBy;
    private User assignee;

    List<TaskHistory> taskHistoryDetail;

    public Task(String title, String description, Date dueDate, Priority priority, TaskType type, User createdBy) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.type = type;
        this.createdBy = createdBy;
        this.assignee = createdBy;
        this.status = Status.OPENED;

        this.id = UUID.randomUUID().toString();
        this.createdTime = System.currentTimeMillis();
        this.taskHistoryDetail = new ArrayList<>();

        this.addHistory("Task is created with id : "+this.id) ;
    }

    public void addHistory(String message){
        TaskHistory history = new TaskHistory(message) ;
        this.taskHistoryDetail.add(history) ;
    }

    public void printTaskDetail(){
        for(TaskHistory history : this.taskHistoryDetail){
            System.out.println(history);
        }
    }




    /* Getters / Setters */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.addHistory("Updated title with info : "+title) ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.addHistory("Updated description with info : "+description) ;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.addHistory("Updated priority from "+this.priority+ " -> "+priority) ;
        this.priority = priority;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.addHistory("Updated status from "+this.status+ " -> "+status) ;
        this.status = status;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.addHistory("Updated assignee from "+this.assignee.getEmail()+ " -> "+assignee.getEmail()) ;
        this.assignee = assignee;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    
    
}
