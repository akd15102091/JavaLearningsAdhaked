package DesignSocialMediaFeed.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignSocialMediaFeed.Comment;
import DesignSocialMediaFeed.Post;
import DesignSocialMediaFeed.User;

public class CommentsService {
    private Map<Post, Map<String, Comment>> commentsMap = new ConcurrentHashMap<>();// <Post, <CommentId, Comment>>

    public void addCommentToPost(Post post, User commentedBy, String commentText){
        Map<String, Comment> comments = this.commentsMap.getOrDefault(post, new ConcurrentHashMap<>());
        Comment comment = new Comment(commentedBy, commentText);
        comments.put(comment.getCommentId(), comment);
        this.commentsMap.put(post, comments);
    }

    public void removeCommentFromPost(Post post, String commentId){
        Map<String, Comment> comments = this.commentsMap.getOrDefault(post, new ConcurrentHashMap<>());
        comments.remove(commentId);
    }

    public List<String> getAllCommentsForPost(Post post){
        List<Comment> comments = new ArrayList<>(this.commentsMap.getOrDefault(post, new ConcurrentHashMap<>()).values());
        List<String> commentsForPost = new ArrayList<>();
        for(Comment comment : comments){
            commentsForPost.add("User "+ comment.getCommentBy() +" has added following comment: "+comment.getCommentText());
        }
        return commentsForPost;
    }
}
