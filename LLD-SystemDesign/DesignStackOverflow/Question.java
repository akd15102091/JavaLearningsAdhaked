package DesignStackOverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"unused"})
public class Question implements Commentable, Votable{
    private String id;
    private String title;
    private String description;
    private Long timestamp;

    private User author;
    private List<Tag> tags;
    private List<Answer> answers;
    private List<Comment> comments;
    private List<Vote> votes;
    private List<Observer> observers = new ArrayList<>();

    public Question(String title, String description, User author, List<Tag> tags) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.author = author;
        this.tags = tags;

        this.timestamp = System.currentTimeMillis();
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();

        this.addObserver(author);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer) ;

        this.notifyObservers("New answer posted! \n");
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(this.comments) ;
    }

    @Override
    public void addComment(Comment comment) {
        this.comments.add(comment) ;
    }

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Answer> getAnswers() {
        return answers;
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
