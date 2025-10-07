package DesignJobSchedulerLLD;

/*
 * Runnable worker that executes a job’s task, logs execution results, and handles retries.
 */
public class JobRunner implements Runnable{
    private Job job;
    private JobSchedulerService scheduler;

    public JobRunner(Job job, JobSchedulerService scheduler) {
        this.job = job;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        Execution execution = scheduler.createExecution(job);
        execution.markRunning();
        job.setStatus(JobStatus.RUNNING);

        try {
            job.getTask().execute();
            execution.markSuccess();
            job.setStatus(JobStatus.SUCCEEDED);
        } catch (Exception e) {
            job.incrementRetry();
            execution.markFailed();
            if (job.getRetryCount() < job.getMaxRetries()) {
                job.setStatus(JobStatus.RETRYING);
                scheduler.retryJob(job);
            } else {
                job.setStatus(JobStatus.FAILED);
            }
        }
    }
}
