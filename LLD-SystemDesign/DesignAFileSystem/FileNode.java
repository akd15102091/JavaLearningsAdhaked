package DesignAFileSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class FileNode implements Serializable {
    String name;
    List<FileVersion> versions = new ArrayList<>();

    FileNode(String name) {
        this.name = name;
    }

    void addVersion(String content) {
        versions.add(new FileVersion(content));
    }

    FileVersion getLatestVersion() {
        if (versions.isEmpty()) throw new RuntimeException("No content in file");
        return versions.get(versions.size() - 1);
    }

    FileVersion getVersion(int version) {
        if (version < 0 || version >= versions.size())
            throw new RuntimeException("Invalid version");
        return versions.get(version);
    }

    int getVersionCount() {
        return versions.size();
    }
}

