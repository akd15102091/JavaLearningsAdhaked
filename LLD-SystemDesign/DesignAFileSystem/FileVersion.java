package DesignAFileSystem;

import java.io.Serializable;
import java.time.LocalDateTime;

class FileVersion implements Serializable {
    String content;
    LocalDateTime timestamp;
    int size;

    public FileVersion(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.size = content.length();
    }

    @Override
    public String toString() {
        return "Version [timestamp=" + timestamp + ", size=" + size + "]";
    }
}
