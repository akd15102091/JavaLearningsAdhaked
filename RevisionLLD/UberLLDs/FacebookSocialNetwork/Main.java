package UberLLDs.FacebookSocialNetwork;

import java.util.List;

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        SocialNetworkService fb = new SocialNetworkService();

        // ===== Create Users =====
        User alice   = fb.createUser("Alice");
        User bob     = fb.createUser("Bob");
        User charlie = fb.createUser("Charlie");

        System.out.println("Users created:");
        System.out.println(alice.getId() + " -> " + alice.getName());
        System.out.println(bob.getId()   + " -> " + bob.getName());
        System.out.println(charlie.getId() + " -> " + charlie.getName());
        System.out.println();

        // ===== Friendships =====
        fb.addFriend(alice.getId(), bob.getId());
        fb.addFriend(alice.getId(), charlie.getId());

        System.out.println("Alice's Friends: " + fb.getFriends(alice.getId()));
        System.out.println();

        // ===== Posts =====
        Post p1 = fb.createPost(alice.getId(), "Alice - Post 1");
        Post p2 = fb.createPost(bob.getId(),   "Bob - Hello everyone");
        Post p3 = fb.createPost(charlie.getId(),"Charlie - Good morning");
        Post p4 = fb.createPost(alice.getId(), "Alice - Post 2");

        // ===== Likes =====
        fb.likePost(bob.getId(), p4.getId());
        fb.likePost(charlie.getId(), p4.getId());

        // ===== News Feed for Alice =====
        System.out.println("=== Alice's News Feed ===");
        List<Post> feed = fb.getNewsFeed(alice.getId(), 10);

        for (Post post : feed) {
            System.out.println(
                    "PostID: " + post.getId() +
                    ", Author: " + post.getAuthorId() +
                    ", Content: \"" + post.getContent() + "\"" +
                    ", Likes: " + fb.likeCount(post.getId())
            );
        }
    }
}
