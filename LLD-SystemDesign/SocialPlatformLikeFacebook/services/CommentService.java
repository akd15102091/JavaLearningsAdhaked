package SocialPlatformLikeFacebook.services;

import SocialPlatformLikeFacebook.Comment;
import SocialPlatformLikeFacebook.Post;

public class CommentService {
    public void addComment(Post post, String userId, String content) {
        Comment comment = new Comment(userId, content);
        post.comments.add(comment);
    }
}
