package DesignAFileSystem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Directory implements Serializable {
    String name;
    Map<String, Directory> subdirectories = new HashMap<>();
    Map<String, FileNode> files = new HashMap<>();

    Directory(String name) {
        this.name = name;
    }

    Directory getOrCreateSubdirectory(String name) {
        return subdirectories.computeIfAbsent(name, Directory::new);
    }

    Directory getSubdirectory(String name) {
        Directory d = subdirectories.get(name);
        if (d == null) throw new RuntimeException("Directory not found: " + name);
        return d;
    }
}

