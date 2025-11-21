package DesignLoggingLibrary;

public class LogEntry {
    private final long timestamp;
    private final String message;
    private final LogLevel level;

    public LogEntry(long ts, String msg, LogLevel lvl) {
        this.timestamp = ts;
        this.message = msg;
        this.level = lvl;
    }

    public long getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public LogLevel getLevel() { return level; }
}
