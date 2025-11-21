package DesignMeetingSchedular;

import java.util.Objects;

public class MeetingRoom {
    private final String id;
    private final String name;
    private final int capacity;

    public MeetingRoom(String id, String name, int capacity) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return "MeetingRoom{" + "id='" + id + '\'' + ", name='" + name + '\'' +
                ", capacity=" + capacity + '}';
    }
}