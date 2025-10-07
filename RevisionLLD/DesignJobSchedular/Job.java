package DesignJobSchedular;

import java.time.Instant;
import java.util.UUID;

@SuppressWarnings("unused")
public class Job {
    private final String jobId;
    private String userId;
    private Task task;
    private boolean oneTime;
    private String cronExpression; // if oneTime is false 
    private Instant runAt;
    private JobStatus status;
    private int maxRetries = 3;
    private int retryCount = 0;

    public Job(String jobId, String userId, Task task, Instant runAt){
        this.jobId = jobId;
        this.userId = userId;
        this.task = task;
        this.oneTime = true;
        this.runAt = runAt;
        this.status = JobStatus.PENDING;
    }
    public Job(String jobId, String userId, Task task, String cronExpression){
        this.jobId = jobId;
        this.userId = userId;
        this.task = task;
        this.oneTime = false;
        this.cronExpression = cronExpression;
        this.status = JobStatus.PENDING;
    }

    public void setStatus(JobStatus status){
        this.status = status;
    }

    public String getJobId(){
        return jobId;
    }

    public String getUserId(){
        return userId;
    }

    public int getRetryCount(){
        return retryCount;
    }

    public boolean isOneTime(){
        return oneTime;
    }
    public String getCronExpression() {
        return this.cronExpression;
    }

    public Task getTask(){
        return task;
    }

    public void incrementRetry(){
        this.retryCount++;
    }

    public int getMaxRetries(){
        return this.maxRetries;
    }

}
