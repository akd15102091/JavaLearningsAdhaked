package DesignJobSchedulerLLD;

import java.time.Instant;

/*
 * Defines a scheduled unit of work: who owns it, what task to run, 
 * when to run (one-time or recurring), and retry policies. Maintains lifecycle JobStatus.
 */
public class Job {
    private String jobId;
    private String userId;
    private Task task;
    private boolean oneTime;
    private Instant runAt;          // for one-time jobs
    private String cronExpression;  // for recurring jobs
    private int maxRetries = 3;
    private int retryCount = 0;
    private JobStatus status = JobStatus.PENDING;

    public Job(String jobId, String userId, Task task, Instant runAt) {
        this.jobId = jobId;
        this.userId = userId;
        this.task = task;
        this.oneTime = true;
        this.runAt = runAt;
    }

    public Job(String jobId, String userId, Task task, String cronExpression) {
        this.jobId = jobId;
        this.userId = userId;
        this.task = task;
        this.oneTime = false;
        this.cronExpression = cronExpression;
    }

    public String getJobId() { return jobId; }
    public String getUserId() { return userId; }
    public Task getTask() { return task; }
    public Instant getRunAt() { return runAt; }
    public String getCronExpression() { return cronExpression; }
    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }
    public int getRetryCount() { return retryCount; }
    public void incrementRetry() { this.retryCount++; }
    public int getMaxRetries() { return maxRetries; }
    public boolean isOneTime() { return oneTime; }
}
