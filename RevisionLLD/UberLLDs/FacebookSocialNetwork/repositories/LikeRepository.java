package UberLLDs.FacebookSocialNetwork.repositories;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LikeRepository {
    private final Map<Long, Set<Long>> likes = new ConcurrentHashMap<>();

    public void like(long uid, long pid) {
        likes.computeIfAbsent(pid, k -> ConcurrentHashMap.newKeySet()).add(uid);
    }

    void unlike(long uid, long pid) {
        likes.getOrDefault(pid, Collections.emptySet()).remove(uid);
    }

    public int count(long pid) {
        return likes.getOrDefault(pid, Collections.emptySet()).size();
    }
}
