package DesignALoggingFrameWork;

public class LogContent {
    private final String message;
    private final long timestamp; 
    private final LogLevel level;

    public LogContent(String message, LogLevel level) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public LogLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "LogContent [message=" + message + ", timestamp=" + timestamp + ", level=" + level + "]";
    }
    
}
