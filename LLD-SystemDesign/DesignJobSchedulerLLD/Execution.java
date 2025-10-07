package DesignJobSchedulerLLD;

import java.time.Instant;

/*
 * Represents a single attempt of a job run. Tracks attempt number, timestamps, and result JobStatus
 */
@SuppressWarnings("unused")
public class Execution {
    private String executionId;
    private String jobId;
    private String userId;
    private JobStatus status;
    private Instant triggeredAt;
    private Instant finishedAt;
    private int attemptNumber;

    public Execution(String executionId, String jobId, String userId, int attemptNumber) {
        this.executionId = executionId;
        this.jobId = jobId;
        this.userId = userId;
        this.attemptNumber = attemptNumber;
        this.status = JobStatus.PENDING;
        this.triggeredAt = Instant.now();
    }

    public void markRunning() {
        this.status = JobStatus.RUNNING;
    }

    public void markSuccess() {
        this.status = JobStatus.SUCCEEDED;
        this.finishedAt = Instant.now();
    }

    public void markFailed() {
        this.status = JobStatus.FAILED;
        this.finishedAt = Instant.now();
    }

    public String getExecutionId() { return executionId; }
    public JobStatus getStatus() { return status; }
    public int getAttemptNumber() { return attemptNumber; }
}
