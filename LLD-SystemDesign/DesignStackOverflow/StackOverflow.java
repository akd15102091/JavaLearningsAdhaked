package DesignStackOverflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})
public class StackOverflow {

    private final Map<String, User> allUsers;
    private final Map<String, Question> allQuestions;
    private final Map<String, Answer> allAnswers;
    private final Map<String, Tag> allTags;

    StackOverflow(){
        this.allUsers = new HashMap<>();
        this.allQuestions = new HashMap<>();
        this.allAnswers = new HashMap<>();
        this.allTags = new HashMap<>();
    }

    public User addUser(String name){
        User user = new User(name) ;
        this.allUsers.put(user.getId(), user) ;
        return user;
    }

    public void addUsers(){
        String[] names = {"Adam", "Alice", "Jack"} ;
        for(int i=0; i<3; i++){
            User user = new User(names[i]) ;
            this.allUsers.put(user.getId(), user) ;
        }
    }

    public Question askQuestion(String title, String des, User user, List<Tag> tags){
        Question question = user.askQuestion(title, des, tags) ;
        this.allQuestions.put(question.getId(), question) ;

        for(Tag tag : tags){
            this.allTags.putIfAbsent(tag.getName(), tag);
        }

        return question;
    }

    public Answer answerQuestion(User user, Question question, String content){
        Answer answer = user.asnwerQuestion(question, content) ;
        this.allAnswers.put(answer.getId(), answer);
        return answer;
    }

    public Comment addComment(User user, Commentable commentable, String content){
        Comment comment = user.addComment(commentable, content) ;
        return comment;
    }

    public void voteQuestion(Question question, User user, int value){
        question.vote(user, value);
    }



    public void printData(){
        for(Map.Entry<String,Question> entry : this.allQuestions.entrySet()){
            Question question = entry.getValue() ;
            System.out.println("Question :: ");
            System.out.println("        Title: "+question.getTitle());
            System.out.println("        Description: "+question.getDescription());
            System.out.println("        Vote count: "+question.getVoteCount());
            System.out.println("Answers are :: ");
            List<Answer> answers = question.getAnswers();
            for(int i=0; i<answers.size(); i++){
                System.out.println("        Answer "+(i+1)+": "+answers.get(i).getDescription());
                System.out.println("            - Given by: "+answers.get(i).getAuthor().getName());
            }
            System.out.println("\n ------------------------------------------------------------------- \n");
        }
    }

}
