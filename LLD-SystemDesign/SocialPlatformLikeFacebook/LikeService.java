package SocialPlatformLikeFacebook;

public class LikeService {
    public void likePost(Post post, String userId) {
        post.likeUserIds.add(userId);
    }

    public void unlikePost(Post post, String userId) {
        post.likeUserIds.remove(userId);
    }
}
