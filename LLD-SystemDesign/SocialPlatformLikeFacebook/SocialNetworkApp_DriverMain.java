package SocialPlatformLikeFacebook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SocialPlatformLikeFacebook.services.AuthService;
import SocialPlatformLikeFacebook.services.CommentService;
import SocialPlatformLikeFacebook.services.FeedService;
import SocialPlatformLikeFacebook.services.FriendshipService;
import SocialPlatformLikeFacebook.services.NotificationService;
import SocialPlatformLikeFacebook.services.PostService;

public class SocialNetworkApp_DriverMain {
    public static void main(String[] args) {
        AuthService authService = AuthService.getInstance();
        NotificationService notificationService = NotificationService.getInstance();

        // Register users
        User alice = authService.register("Alice", "alice@example.com", "password123");
        User bob = authService.register("Bob", "bob@example.com", "password123");
        User charlie = authService.register("Charlie", "charlie@example.com", "password123");

        Map<String, User> userStore = new HashMap<>();
        userStore.put(alice.userId, alice);
        userStore.put(bob.userId, bob);
        userStore.put(charlie.userId, charlie);

        FriendshipService friendshipService = new FriendshipService(userStore);
        PostService postService = new PostService();
        FeedService feedService = new FeedService(userStore, postService);
        LikeService likeService = new LikeService();
        CommentService commentService = new CommentService();

        // Login as Alice
        User loggedInAlice = authService.login("alice@example.com", "password123");
        System.out.println("Logged in as: " + loggedInAlice.name);

        // Alice sends friend request to Bob and Charlie
        friendshipService.sendRequest(alice.userId, bob.userId);
        friendshipService.sendRequest(alice.userId, charlie.userId);

        // Bob accepts Alice's request
        List<FriendRequest> bobsRequests = friendshipService.requestsByUser.get(bob.userId);
        friendshipService.acceptRequest(bob.userId, bobsRequests.get(0).requestId);

        // Charlie accepts Alice's request
        List<FriendRequest> charliesRequests = friendshipService.requestsByUser.get(charlie.userId);
        friendshipService.acceptRequest(charlie.userId, charliesRequests.get(0).requestId);

        // Alice creates a post
        Post post1 = postService.createPost(alice.userId, "Hello, world!", Arrays.asList());

        // Bob likes Alice's post
        likeService.likePost(post1, bob.userId);
        notificationService.notify(alice.userId, "Bob liked your post.");

        // Charlie comments on Alice's post
        commentService.addComment(post1, charlie.userId, "Welcome to the platform!");
        notificationService.notify(alice.userId, "Charlie commented on your post.");

        // Charlie creates a post
        postService.createPost(charlie.userId, "What a beautiful day!", Arrays.asList());

        // Alice views her newsfeed
        List<Post> feed = feedService.getNewsFeed(alice.userId);
        System.out.println("\n=== Alice's Newsfeed ===");
        for (Post post : feed) {
            System.out.println("Post by " + userStore.get(post.authorId).name + ": " + post.content);
            System.out.println("Likes: " + post.likeUserIds.size());
            for (Comment comment : post.comments) {
                System.out.println("- Comment by " + userStore.get(comment.userId).name + ": " + comment.content);
            }
            System.out.println();
        }

        // View notifications for Alice
        System.out.println("=== Alice's Notifications ===");
        for (Notification n : notificationService.getNotifications(alice.userId)) {
            System.out.println(n.timestamp + ": " + n.message);
        }
    }
}
