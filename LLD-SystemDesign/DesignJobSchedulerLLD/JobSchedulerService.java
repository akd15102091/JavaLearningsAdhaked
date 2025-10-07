package DesignJobSchedulerLLD;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/*
 * Central manager that stores jobs, users, and executions. Handles job creation, scheduling, retries, and querying job/execution statuses.
 */
public class JobSchedulerService {
    private Map<String, Job> jobs = new ConcurrentHashMap<>();
    private Map<String, List<Execution>> executions = new ConcurrentHashMap<>();
    private WorkerPool workerPool = new WorkerPool();
    private DelayQueue<ScheduledJob> delayQueue = new DelayQueue<>();

    // Create one-time job
    public String createOneTimeJob(String userId, Task task, Instant runAt) {
        String jobId = UUID.randomUUID().toString();
        Job job = new Job(jobId, userId, task, runAt);
        jobs.put(jobId, job);
        delayQueue.offer(new ScheduledJob(job, runAt.toEpochMilli()));
        return jobId;
    }

    // Create recurring job (simplified cron)
    public String createCronJob(String userId, Task task, String cronExpr) {
        String jobId = UUID.randomUUID().toString();
        Job job = new Job(jobId, userId, task, cronExpr);
        jobs.put(jobId, job);
        delayQueue.offer(new ScheduledJob(job, System.currentTimeMillis() + 5000));
        return jobId;
    }

    public Job getJob(String jobId) {
        return jobs.get(jobId);
    }

    public List<Execution> getExecutions(String jobId) {
        return executions.getOrDefault(jobId, new ArrayList<>());
    }

    // Internal: create new execution record
    public Execution createExecution(Job job) {
        String execId = UUID.randomUUID().toString();
        Execution exec = new Execution(execId, job.getJobId(), job.getUserId(), job.getRetryCount() + 1);
        executions.computeIfAbsent(job.getJobId(), k -> new ArrayList<>()).add(exec);
        return exec;
    }

    // Retry handler
    public void retryJob(Job job) {
        delayQueue.offer(new ScheduledJob(job, System.currentTimeMillis() + 2000)); // retry after 2 sec
    }

    // Start dispatcher loop
    public void start() {
        Thread dispatcher = new Thread(() -> {
            while (true) {
                try {
                    ScheduledJob scheduled = delayQueue.take();
                    Job job = scheduled.getJob();
                    workerPool.submit(new JobRunner(job, this));

                    // reschedule if recurring
                    if (!job.isOneTime()) {
                        long next = scheduled.computeNextRun();
                        if (next > 0) {
                            delayQueue.offer(new ScheduledJob(job, next));
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        dispatcher.start();
    }
}
