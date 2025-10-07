package DesignALoggingFrameWork.logappender;

import DesignALoggingFrameWork.LogContent;

public class ConsoleLogAppender implements LogAppender{

    @Override
    public void append(LogContent logContent) {
        System.out.println("Console log appender...");
        System.out.println("Content is : "+ logContent.toString());
    }
    
}
