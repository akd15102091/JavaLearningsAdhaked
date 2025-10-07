package DesignStackOverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"unused"})
public class Answer implements Commentable, Votable{
    private String id;
    private String description;
    private int numOfVote = 0;
    private User author;
    private long timestamp;

    private List<Vote> votes;
    private List<Comment> comments;

    public Answer(String description, User author) {
        this.description = description;
        this.author = author;

        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        
        this.votes = new ArrayList<>();
        this.comments = new ArrayList<>() ;
    }

    public void upvote(){
        this.numOfVote++;
    }

    public void downvote(){
        this.numOfVote--;
    }

    public void addComment(Comment comment){
        this.comments.add(comment)  ;
    }

    @Override
    public List<Comment> getComments() {
        return this.comments;
    }

    public String getId(){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOfVote() {
        return numOfVote;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public void vote(User user, int value) {
        if (value != 1 && value != -1) {
            throw new IllegalArgumentException("Vote value must be either 1 or -1");
        }
        votes.removeIf(v -> v.getUser().equals(user));
        votes.add(new Vote(user, value));
        author.updateReputation(value * 5);  // +5 for upvote, -5 for downvote
    }

    @Override
    public int getVoteCount() {
        return votes.stream().mapToInt(Vote::getValue).sum();
    }



    
    
}
