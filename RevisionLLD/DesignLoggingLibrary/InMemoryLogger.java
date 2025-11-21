package DesignLoggingLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class InMemoryLogger {
    private final NavigableMap<Long, List<LogEntry>> logs = new TreeMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // SET method
    public void log(String message, LogLevel level, long timestamp) {
        lock.writeLock().lock();
        try {
            logs.computeIfAbsent(timestamp, t -> new ArrayList<>()).add(new LogEntry(timestamp, message, level));
        } finally {
            lock.writeLock().unlock();
        }
    }

    // GET logs by time range
    public List<LogEntry> getLogs(long start, long end) {
        lock.readLock().lock();
        try {
            return logs.subMap(start, true, end, true)
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

                /*
                 * The above whole line:
                    ✔ gets logs between a time range
                    ✔ merges all lists of logs into a single list
                    ✔ returns a flat List<LogEntry>
                 */
        } finally {
            lock.readLock().unlock();
        }
    }

    // GET logs by level
    public List<LogEntry> getLogs(LogLevel level) {
        lock.readLock().lock();
        try {
            List<LogEntry> result = new ArrayList<>();
            for (List<LogEntry> list : logs.values()) {
                for (LogEntry entry : list) {
                    if (entry.getLevel() == level) result.add(entry);
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    // GET logs by time + level
    public List<LogEntry> getLogs(long start, long end, LogLevel level) {
        return getLogs(start, end)
                .stream()
                .filter(e -> e.getLevel() == level)
                .collect(Collectors.toList());
    }
}
