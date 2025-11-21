package DesignMeetingSchedular;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MeetingScheduler {
    private final Map<String, Meeting> meetingsById = new ConcurrentHashMap<>();
    private final Map<String, NavigableSet<Meeting>> meetingsByRoom = new ConcurrentHashMap<>();
    private final Map<String, NavigableSet<Meeting>> meetingsByUser = new ConcurrentHashMap<>();
    private final LockManager lockManager = new LockManager();



    // ----- Booking -----

    public String bookMeeting(MeetingRoom room, User organizer, List<User> participants, TimeRange range) {
        List<User> all = new ArrayList<>(participants);
        if (participants.stream().noneMatch(u -> u.getId().equals(organizer.getId()))) {
            all.add(organizer);
        }

        // Lock ordering (deadlock-safe)
        Set<String> lockKeys = new TreeSet<>();
        lockKeys.add("ROOM:" + room.getId());
        for (User u : all) lockKeys.add("USER:" + u.getId());

        List<ReentrantReadWriteLock.WriteLock> acquired = new ArrayList<>();

        try {
            for (String key : lockKeys) {
                ReentrantReadWriteLock.WriteLock wl = lockManager.getLock(key).writeLock();
                wl.lock();
                acquired.add(wl);
            }

            // Check room conflict
            NavigableSet<Meeting> roomSet = meetingsByRoom.computeIfAbsent(room.getId(), x -> new TreeSet<>());
            if (hasConflict(roomSet, range)){
                throw new IllegalStateException("Room conflict");
            }

            // Check user conflicts
            for (User u : all) {
                NavigableSet<Meeting> userSet = meetingsByUser.computeIfAbsent(u.getId(), x -> new TreeSet<>());
                if (hasConflict(userSet, range)){
                    throw new IllegalStateException("User conflict for " + u.getId());
                }
            }

            // Create meeting
            String mid = UUID.randomUUID().toString();
            Meeting meeting = new Meeting(mid, room, organizer, all, range);

            meetingsById.put(mid, meeting);
            roomSet.add(meeting);
            for (User u : all) {
                meetingsByUser.computeIfAbsent(u.getId(), x -> new TreeSet<>()).add(meeting);
            }

            return mid;

        } finally {
            for (int i = acquired.size() - 1; i >= 0; i--) {
                acquired.get(i).unlock();
            }
        }
    }


    // ----- Cancellation -----

    public void cancelMeeting(String meetingId) {
        Meeting meeting = meetingsById.get(meetingId);
        if (meeting == null) return;

        MeetingRoom room = meeting.getRoom();
        List<User> participants = meeting.getParticipants();

        Set<String> lockKeys = new TreeSet<>();
        lockKeys.add("ROOM:" + room.getId());
        for (User u : participants) lockKeys.add("USER:" + u.getId());

        List<ReentrantReadWriteLock.WriteLock> acquired = new ArrayList<>();

        try {
            for (String key : lockKeys) {
                ReentrantReadWriteLock.WriteLock wl = lockManager.getLock(key).writeLock();
                wl.lock();
                acquired.add(wl);
            }

            Meeting m = meetingsById.remove(meetingId);
            if (m == null) return;

            m.cancel();
            meetingsByRoom.get(room.getId()).remove(m);
            for (User u : participants) meetingsByUser.get(u.getId()).remove(m);

        } finally {
            for (int i = acquired.size() - 1; i >= 0; i--) {
                acquired.get(i).unlock();
            }
        }
    }

    // ----- Query -----

    public List<Meeting> getMeetingsForRoom(String roomId, LocalDateTime from, LocalDateTime to) {
        ReentrantReadWriteLock.ReadLock read = lockManager.getLock("ROOM:" + roomId).readLock();
        read.lock();
        try {
            NavigableSet<Meeting> set = meetingsByRoom.get(roomId);
            if (set == null) return List.of();

            Meeting dummyFrom = dummyMeeting(from);
            Meeting dummyTo = dummyMeeting(to);

            List<Meeting> result = new ArrayList<>();
            TimeRange query = new TimeRange(from, to);

            for (Meeting m : set.subSet(dummyFrom, true, dummyTo, false)) {
                if (m.getStatus() == MeetingStatus.ACTIVE &&
                    m.getTimeRange().overlaps(query)) {
                    result.add(m);
                }
            }
            return result;

        } finally {
            read.unlock();
        }
    }

    public List<Meeting> getMeetingsForUser(String userId, LocalDateTime from, LocalDateTime to) {
        ReentrantReadWriteLock.ReadLock read = lockManager.getLock("USER:" + userId).readLock();
        read.lock();
        try {
            NavigableSet<Meeting> set = meetingsByUser.get(userId);
            if (set == null) return List.of();

            Meeting dummyFrom = dummyMeeting(from);
            Meeting dummyTo = dummyMeeting(to);

            List<Meeting> result = new ArrayList<>();
            TimeRange query = new TimeRange(from, to);

            for (Meeting m : set.subSet(dummyFrom, true, dummyTo, false)) {
                if (m.getStatus() == MeetingStatus.ACTIVE &&
                    m.getTimeRange().overlaps(query)) {
                    result.add(m);
                }
            }
            return result;

        } finally {
            read.unlock();
        }
    }

    // ----- Helpers -----

    private boolean hasConflict(NavigableSet<Meeting> set, TimeRange newRange) {
        if (set.isEmpty()) return false;

        Meeting probe = dummyMeeting(newRange.getStart());

        Meeting floor = set.floor(probe);
        if (floor != null && floor.getStatus() == MeetingStatus.ACTIVE && floor.getTimeRange().overlaps(newRange)) {
            return true;
        }

        Meeting ceil = set.ceiling(probe);
        return ceil != null && ceil.getStatus() == MeetingStatus.ACTIVE && ceil.getTimeRange().overlaps(newRange);
    }

    private Meeting dummyMeeting(LocalDateTime start) {
        MeetingRoom r = new MeetingRoom("DUMMY", "dummy", 0);
        User u = new User("DUMMY", "dummy");
        TimeRange tr = new TimeRange(start, start.plusSeconds(1));
        return new Meeting("__dummy__" + start.toString(), r, u, List.of(u), tr);
    }



}
