package DesignJobSchedular;

public class JobRunner implements Runnable{

    private Job job;
    private SchedularService scheduler;

    public JobRunner(Job job, SchedularService scheduler) {
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
            execution.markCompleted();
            job.setStatus(JobStatus.COMPLETED);
        } catch (Exception e) {
            job.incrementRetry();
            execution.markFailed();

            if(job.getRetryCount() < job.getMaxRetries()){
                scheduler.retryJob(job);
                job.setStatus(JobStatus.RETRYING);
            }
            else{
                job.setStatus(JobStatus.FAILED);
            }
        }
    }
    
}
