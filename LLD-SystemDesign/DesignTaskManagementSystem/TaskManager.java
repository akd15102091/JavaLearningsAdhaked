package DesignTaskManagementSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public class TaskManager {
    private static TaskManager instance;
    private final Map<String, Task> tasks;
    private final Map<String, List<Task>> userTasks;

    private TaskManager(){
        this.tasks = new ConcurrentHashMap<>();
        this.userTasks = new ConcurrentHashMap<>();
    }

    public synchronized static TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    public void createTask(Task newTask){
        this.tasks.put(newTask.getId(), newTask) ;
        // this.assignTaskToUser(newTask.getCreatedBy(), newTask);
    }

    public void updateTask(Task updatedTask) {
        Task existingTask = tasks.get(updatedTask.getId());
        if (existingTask != null) {
            synchronized (existingTask) {
                if(!existingTask.getTitle().equals(updatedTask.getTitle())){
                    existingTask.setTitle(updatedTask.getTitle());
                }
                if(!existingTask.getDescription().equals(updatedTask.getDescription())){
                    existingTask.setDescription(updatedTask.getDescription());
                }
                if(!existingTask.getDueDate().equals(updatedTask.getDueDate())){
                    existingTask.setDueDate(updatedTask.getDueDate());
                }
                if(!existingTask.getDueDate().equals(updatedTask.getDueDate())){
                    existingTask.setDueDate(updatedTask.getDueDate());
                }
                if(!existingTask.getPriority().equals(updatedTask.getPriority())){
                    existingTask.setPriority(updatedTask.getPriority());
                }
                // if(existingTask.getStatus().equals(updatedTask.getStatus())){
                //     existingTask.setStatus(updatedTask.getStatus());
                // }
                
                User previousUser = existingTask.getAssignee();
                User newUser = updatedTask.getAssignee();
                if (!previousUser.equals(newUser)) {
                    unassignTaskFromUser(previousUser, existingTask);
                    assignTaskToUser(newUser, existingTask);
                }
            }
        }
    }

    public void deleteTask(String taskId) {
        Task task = tasks.remove(taskId);
        if (task != null) {
            unassignTaskFromUser(task.getAssignee(), task);
        }
    }

    public List<Task> searchTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getTitle().contains(keyword) || task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    public List<Task> filterTasks(Status status, Date startDate, Date endDate, Priority priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getStatus() == status &&
                    task.getDueDate().compareTo(startDate) >= 0 &&
                    task.getDueDate().compareTo(endDate) <= 0 &&
                    task.getPriority().equals(priority)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public void markTaskAsCompleted(String taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            synchronized (task) {
                task.setStatus(Status.COMPLETED);
            }
        }
    }

    private void assignTaskToUser(User user, Task task) {
        task.setAssignee(user);
        userTasks.computeIfAbsent(user.getId(), k -> new CopyOnWriteArrayList<>()).add(task);
    }

    private void unassignTaskFromUser(User user, Task task) {
        List<Task> tasks = userTasks.get(user.getId());
        if (tasks != null) {
            tasks.remove(task);
        }
    }
}
