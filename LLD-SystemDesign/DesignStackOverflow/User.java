package DesignStackOverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Observer{
    private String id;
    private String name;
    private int reputation;

    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;

    private static final int QUESTION_REPUTATION = 5;
    private static final int ANSWER_REPUTATION = 10;
    private static final int COMMENT_REPUTATION = 2;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.reputation = 0;

        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Question askQuestion(String title, String description, List<Tag> tags){
        Question question = new Question(title, description, this, tags) ;
        this.questions.add(question) ;
        this.updateReputation(QUESTION_REPUTATION); // adding reputation for asking question
        return question;
    }

    public Answer asnwerQuestion(Question question, String description){
        Answer answer = new Answer(description, this) ;
        this.answers.add(answer) ;
        question.addAnswer(answer);
        this.updateReputation(ANSWER_REPUTATION); 
        return answer;
    }

    public Comment addComment(Commentable commentable, String content){
        Comment comment = new Comment(content, this);
        this.comments.add(comment) ;
        commentable.addComment(comment); ;
        this.updateReputation(COMMENT_REPUTATION);
        return comment;
    }

    public synchronized void updateReputation(int value){
        this.reputation +=value;

        if(this.reputation < 0){
            this.reputation = 0;
        }

    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received notification: " + message);
    }

    

    
}
