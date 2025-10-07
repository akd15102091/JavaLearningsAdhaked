package DesignTaskManagementSystem;

import java.util.UUID;

public class TaskHistory {
    private String id;
    private long timestamp;
    private String message;

    public TaskHistory(String message) {
        this.message = message;
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "TaskHistory [id=" + id + ", timestamp=" + timestamp + ", message=" + message + "]";
    }
}
