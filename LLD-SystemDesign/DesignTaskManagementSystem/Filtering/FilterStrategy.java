package DesignTaskManagementSystem.Filtering;

import java.util.List;

import DesignTaskManagementSystem.Task;

public interface FilterStrategy {
    public List<Task> filterTasks(List<Task> tasks) ;
}
