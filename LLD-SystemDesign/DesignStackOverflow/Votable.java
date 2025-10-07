package DesignStackOverflow;

public interface Votable {
    void vote(User user, int value);
    int getVoteCount();
}
