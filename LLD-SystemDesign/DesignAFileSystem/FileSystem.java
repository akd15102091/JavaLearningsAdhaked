package DesignAFileSystem;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class FileSystem implements Serializable {
    private Directory root = new Directory("/");
    private final transient ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String STORAGE_FILE = "DesignAFileSystem/filesystem.ser";

    public FileSystem() {
        loadFromDisk();
    }

    public void createFile(String path) {
        lock.writeLock().lock();
        try {
            String[] parts = path.split("/");
            Directory dir = root;
            for (int i = 1; i < parts.length - 1; i++) {
                dir = dir.getOrCreateSubdirectory(parts[i]);
            }
            String fileName = parts[parts.length - 1];
            dir.files.put(fileName, new FileNode(fileName));
            saveToDisk();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void writeFile(String path, String content) {
        lock.writeLock().lock();
        try {
            FileNode file = getFileNode(path);
            file.addVersion(content);
            saveToDisk();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String readFile(String path) {
        return readFile(path, -1);
    }

    public String readFile(String path, int version) {
        lock.readLock().lock();
        try {
            FileNode file = getFileNode(path);
            FileVersion fv = (version < 0) ? file.getLatestVersion() : file.getVersion(version);
            return fv.content;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<String> getFileMetadata(String path) {
        lock.readLock().lock();
        try {
            FileNode file = getFileNode(path);
            List<String> metadata = new ArrayList<>();
            for (int i = 0; i < file.getVersionCount(); i++) {
                metadata.add(i + ": " + file.getVersion(i).toString());
            }
            return metadata;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void deleteFile(String path) {
        lock.writeLock().lock();
        try {
            String[] parts = path.split("/");
            Directory dir = root;
            for (int i = 1; i < parts.length - 1; i++) {
                dir = dir.getSubdirectory(parts[i]);
            }
            dir.files.remove(parts[parts.length - 1]);
            saveToDisk();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<String> listFiles(String path) {
        lock.readLock().lock();
        try {
            Directory dir = getDirectory(path);
            List<String> result = new ArrayList<>();
            result.addAll(dir.files.keySet());
            result.addAll(dir.subdirectories.keySet());
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void rename(String sourcePath, String newName) {
        lock.writeLock().lock();
        try {
            String[] parts = sourcePath.split("/");
            Directory dir = root;
            for (int i = 1; i < parts.length - 1; i++) {
                dir = dir.getSubdirectory(parts[i]);
            }
            String oldName = parts[parts.length - 1];
            if (dir.files.containsKey(oldName)) {
                FileNode file = dir.files.remove(oldName);
                file.name = newName;
                dir.files.put(newName, file);
            } else if (dir.subdirectories.containsKey(oldName)) {
                Directory subDir = dir.subdirectories.remove(oldName);
                subDir.name = newName;
                dir.subdirectories.put(newName, subDir);
            } else {
                throw new RuntimeException("File or directory not found: " + oldName);
            }
            saveToDisk();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void move(String sourcePath, String destinationDirPath) {
        lock.writeLock().lock();
        try {
            String[] parts = sourcePath.split("/");
            Directory sourceParent = root;
            for (int i = 1; i < parts.length - 1; i++) {
                sourceParent = sourceParent.getSubdirectory(parts[i]);
            }
            String itemName = parts[parts.length - 1];
            Directory destinationDir = getDirectory(destinationDirPath);

            if (sourceParent.files.containsKey(itemName)) {
                FileNode file = sourceParent.files.remove(itemName);
                destinationDir.files.put(file.name, file);
            } else if (sourceParent.subdirectories.containsKey(itemName)) {
                Directory dir = sourceParent.subdirectories.remove(itemName);
                destinationDir.subdirectories.put(dir.name, dir);
            } else {
                throw new RuntimeException("Item not found: " + itemName);
            }
            saveToDisk();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // --- Internal Helpers ---
    private FileNode getFileNode(String path) {
        String[] parts = path.split("/");
        Directory dir = root;
        for (int i = 1; i < parts.length - 1; i++) {
            dir = dir.getSubdirectory(parts[i]);
        }
        FileNode file = dir.files.get(parts[parts.length - 1]);
        if (file == null) throw new RuntimeException("File not found");
        return file;
    }

    private Directory getDirectory(String path) {
        String[] parts = path.split("/");
        Directory dir = root;
        for (int i = 1; i < parts.length; i++) {
            dir = dir.getSubdirectory(parts[i]);
        }
        return dir;
    }

    // --- Persistence ---
    private void saveToDisk() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(root);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file system", e);
        }
    }

    private void loadFromDisk() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            root = (Directory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load file system", e);
        }
    }
}

