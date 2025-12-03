package UberLLDs.FacebookSocialNetwork.repositories;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FriendRepository {
    private final Map<Long, Set<Long>> graph = new ConcurrentHashMap<>();
    private final Map<Long, Object> locks = new ConcurrentHashMap<>();

    private Object getLock(long userId) {
        return locks.computeIfAbsent(userId, k -> new Object());
    }

    public void addFriend(long u1, long u2) {
        if (u1 == u2) return;

        long first = Math.min(u1, u2);
        long second = Math.max(u1, u2);

        Object lock1 = getLock(first);
        Object lock2 = getLock(second);

        synchronized (lock1) {
            synchronized (lock2) {
                graph.computeIfAbsent(u1, x -> ConcurrentHashMap.newKeySet()).add(u2);
                graph.computeIfAbsent(u2, x -> ConcurrentHashMap.newKeySet()).add(u1);
            }
        }
    }

    public Set<Long> getFriends(long user) {
        return new HashSet<>(graph.getOrDefault(user, Collections.emptySet()));
    }
}
