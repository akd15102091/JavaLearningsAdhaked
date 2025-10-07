package DesignALoggingFrameWork;

import DesignALoggingFrameWork.logappender.FileLogAppender;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance() ;

        logger.info("This is the info level message...");
        logger.warning("This is the warning level message...");

        // Changing log level and appender
        LoggerConfig config = new LoggerConfig(LogLevel.DEBUG, new FileLogAppender("app.log"));
        logger.setLoggerConfig(config);

        logger.debug("This is a debug message");
        logger.info("This is an information message");
    }
}
