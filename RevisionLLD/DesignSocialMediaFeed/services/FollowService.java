package DesignSocialMediaFeed.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import DesignSocialMediaFeed.FollowRequest;
import DesignSocialMediaFeed.User;

public class FollowService {
    private Map<User, Set<User>> followersMap = new ConcurrentHashMap<>(); // who are the followers of user
    private Map<User, Map<User, FollowRequest>> pendingRequestsMap = new ConcurrentHashMap<>();// <following, <follower, followrequest>>


    public void addFollower(User follower, User following){
        followersMap.computeIfAbsent(following, k -> ConcurrentHashMap.<User>newKeySet()).add(follower);
    }

    public List<User> getFollowers(User user){
        return new ArrayList<>(this.followersMap.getOrDefault(user, null));
    }

    public void sendFollowRequest(User follower, User following){
        FollowRequest request = new FollowRequest(follower, following);
        this.pendingRequestsMap.computeIfAbsent(following, k -> new ConcurrentHashMap<>()).computeIfAbsent(follower, k -> request);
    }

    public void acceptFriendRequest(User following, User follower){
        Map<User, FollowRequest> requests = this.pendingRequestsMap.getOrDefault(following, new ConcurrentHashMap<>());
        requests.remove(follower);

        // add follower 
        this.addFollower(following, follower);
        this.addFollower(follower, following);
    }
    
}
