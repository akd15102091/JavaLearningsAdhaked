package SocialPlatformLikeFacebook.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SocialPlatformLikeFacebook.FriendRequest;
import SocialPlatformLikeFacebook.User;

public class FriendshipService {
    public Map<String, User> userStore;
    public Map<String, List<FriendRequest>> requestsByUser = new HashMap<>();

    public FriendshipService(Map<String, User> userStore) {
        this.userStore = userStore;
    }

    public void sendRequest(String fromUserId, String toUserId) {
        FriendRequest request = new FriendRequest(fromUserId, toUserId);
        requestsByUser.computeIfAbsent(toUserId, k -> new ArrayList<>()).add(request);
    }

    public void acceptRequest(String userId, String requestId) {
        List<FriendRequest> requests = requestsByUser.get(userId);
        for (FriendRequest req : requests) {
            if (req.requestId.equals(requestId)) {
                req.isAccepted = true;
                User fromUser = userStore.get(req.fromUserId);
                User toUser = userStore.get(req.toUserId);
                fromUser.friends.add(toUser.userId);
                toUser.friends.add(fromUser.userId);
                return;
            }
        }
    }
}
