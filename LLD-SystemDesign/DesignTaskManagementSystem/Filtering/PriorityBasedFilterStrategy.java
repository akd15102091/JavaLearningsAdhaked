package DesignTaskManagementSystem.Filtering;

import java.util.List;
import java.util.stream.Collectors;

import DesignTaskManagementSystem.Priority;
import DesignTaskManagementSystem.Task;

public class PriorityBasedFilterStrategy implements FilterStrategy{

    private Priority priority;

    public PriorityBasedFilterStrategy(Priority priority){
        this.priority = priority;
    }

    @Override
    public List<Task> filterTasks(List<Task> tasks) {
        return tasks.stream().filter(task -> task.getPriority().equals(priority)).collect(Collectors.toList());
    }
    
}
