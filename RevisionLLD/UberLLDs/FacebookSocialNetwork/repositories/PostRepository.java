package UberLLDs.FacebookSocialNetwork.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import UberLLDs.FacebookSocialNetwork.Post;

public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> postsByUser = new ConcurrentHashMap<>();

    public Post save(Post p) {
        posts.put(p.getId(), p);
        postsByUser
                .computeIfAbsent(p.getAuthorId(), k -> new CopyOnWriteArrayList<>())
                .add(p.getId());
        return p;
    }

    Post get(long id) { return posts.get(id); }

    public List<Post> getRecentPosts(long uid, int limit) {
        List<Long> ids = postsByUser.getOrDefault(uid, List.of());
        List<Post> out = new ArrayList<>();

        for (int i = ids.size() - 1; i >= 0 && out.size() < limit; i--) {
            out.add(posts.get(ids.get(i)));
        }
        return out;
    }
}
