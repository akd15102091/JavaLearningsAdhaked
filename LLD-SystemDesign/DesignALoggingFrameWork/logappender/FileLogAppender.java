package DesignALoggingFrameWork.logappender;

import java.io.FileWriter;
import java.io.IOException;

import DesignALoggingFrameWork.LogContent;

public class FileLogAppender implements LogAppender{
    private String filePath;

    public FileLogAppender(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void append(LogContent logContent) {
        System.out.println("File log appender...");
        System.out.println("Content is : "+ logContent.toString());

        try (FileWriter writer = new FileWriter("DesignALoggingFrameWork/"+filePath, true)) {
            writer.write(logContent.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
