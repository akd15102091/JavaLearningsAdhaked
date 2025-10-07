package DesignALoggingFrameWork;

import DesignALoggingFrameWork.logappender.ConsoleLogAppender;

public class Logger {
    private static final Logger instance = new Logger();
    LoggerConfig loggerConfig ;

    private Logger() {
        // Private constructor to enforce singleton pattern
        this.loggerConfig = new LoggerConfig(LogLevel.INFO, new ConsoleLogAppender());
    }

    public LoggerConfig getLoggerConfig() {
        return loggerConfig;
    }

    public void setLoggerConfig(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    public static Logger getInstance() {
        return instance;
    }


    public void log(LogLevel level, String message){
        if(level.ordinal() >= this.loggerConfig.getLogLevel().ordinal()){
            LogContent content = new LogContent(message, level) ;
            this.loggerConfig.getLogAppender().append(content);
        }
        else{
            System.out.println("Log request skipped");
        }
        
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }


}
