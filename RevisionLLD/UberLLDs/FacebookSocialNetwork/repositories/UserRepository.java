package UberLLDs.FacebookSocialNetwork.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import UberLLDs.FacebookSocialNetwork.User;

public class UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    public User save(User u) {
        users.put(u.getId(), u);
        return u;
    }

    User get(long id) { return users.get(id); }
}
