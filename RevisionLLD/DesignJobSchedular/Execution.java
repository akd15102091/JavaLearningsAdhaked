package DesignJobSchedular;

import java.time.Instant;

@SuppressWarnings("unused")
public class Execution {
    private String executionId;
    private String jobId;
    private String taskId;
    private String userId;
    private JobStatus status;
    private Instant triggeredAt;
    private Instant finishedAt;
    private int attemptNumber;

    public Execution(String executionId, String jobId, String userId, int attemptNumber){
        this.executionId = executionId;
        this.jobId = jobId;
        this.userId = userId;
        this.attemptNumber = attemptNumber;
        this.status = JobStatus.PENDING;
        this.triggeredAt = Instant.now();
    }

    public void markRunning(){
        this.status = JobStatus.RUNNING;
    }

    public void markFailed(){
        this.status = JobStatus.FAILED;
    }

    public void markCompleted(){
        this.status = JobStatus.COMPLETED;
    }
}
