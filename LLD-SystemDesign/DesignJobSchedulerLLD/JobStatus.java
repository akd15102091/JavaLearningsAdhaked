package DesignJobSchedulerLLD;

/*
 * Standardized states (PENDING, RUNNING, SUCCEEDED, FAILED, RETRYING, SCHEDULED) used both for Job lifecycle and Execution outcomes.
 */
public enum JobStatus {
    PENDING,
    SCHEDULED,
    RUNNING,
    SUCCEEDED,
    FAILED,
    RETRYING
}
