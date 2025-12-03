package UberLLDs.FacebookSocialNetwork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import UberLLDs.FacebookSocialNetwork.repositories.FriendRepository;
import UberLLDs.FacebookSocialNetwork.repositories.LikeRepository;
import UberLLDs.FacebookSocialNetwork.repositories.PostRepository;
import UberLLDs.FacebookSocialNetwork.repositories.UserRepository;

public class SocialNetworkService {
    private final UserRepository userRepo = new UserRepository();
    private final FriendRepository friendRepo = new FriendRepository();
    private final PostRepository postRepo = new PostRepository();
    private final LikeRepository likeRepo = new LikeRepository();

    private final AtomicLong userIdGen = new AtomicLong(1);
    private final AtomicLong postIdGen = new AtomicLong(1);

    // ---- User ----
    User createUser(String name) {
        User u = new User(userIdGen.getAndIncrement(), name);
        return userRepo.save(u);
    }

    // ---- Friendship ----
    void addFriend(long u1, long u2) {
        friendRepo.addFriend(u1, u2);
    }

    Set<Long> getFriends(long user) {
        return friendRepo.getFriends(user);
    }

    // ---- Posts ----
    Post createPost(long authorId, String content) {
        Post p = new Post(
                postIdGen.getAndIncrement(),
                authorId,
                content,
                System.currentTimeMillis()
        );
        return postRepo.save(p);
    }

    // ---- Likes ----
    void likePost(long uid, long pid) { likeRepo.like(uid, pid); }
    int likeCount(long pid) { return likeRepo.count(pid); }

    // ---- News Feed (k-way merge) ----
    List<Post> getNewsFeed(long uid, int limit) {
        Set<Long> userIds = new HashSet<>(getFriends(uid));
        userIds.add(uid);

        PriorityQueue<Post> maxHeap = new PriorityQueue<>(
                (a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt())
        );

        for (long u : userIds) {
            maxHeap.addAll(postRepo.getRecentPosts(u, 20));
        }

        List<Post> feed = new ArrayList<>();
        while (!maxHeap.isEmpty() && feed.size() < limit) {
            feed.add(maxHeap.poll());
        }
        return feed;
    }
}
