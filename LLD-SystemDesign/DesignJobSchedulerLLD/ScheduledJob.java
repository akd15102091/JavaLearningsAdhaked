package DesignJobSchedulerLLD;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduledJob implements Delayed {
    private Job job;
    private long scheduledTime;

    public ScheduledJob(Job job, long scheduledTime) {
        this.job = job;
        this.scheduledTime = scheduledTime;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delay = scheduledTime - System.currentTimeMillis();
        return unit.convert(delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(this.scheduledTime, ((ScheduledJob) other).scheduledTime);
    }

    // Compute next run time based on job's cron expression.
    // Supports simple cron format: "*/Ns" or "*/Nm".
    public long computeNextRun() {
        String cron = job.getCronExpression();
        if (cron == null || cron.isEmpty()) return -1;

        if (cron.endsWith("s")) { // seconds
            int interval = Integer.parseInt(cron.substring(2, cron.length() - 1));
            return System.currentTimeMillis() + interval * 1000L;
        } else if (cron.endsWith("m")) { // minutes
            int interval = Integer.parseInt(cron.substring(2, cron.length() - 1));
            return System.currentTimeMillis() + interval * 60_000L;
        }
        return -1; // unsupported
    }
}
