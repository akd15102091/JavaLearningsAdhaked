package DesignStackOverflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused"})
public class DriverMain {
    public static void main(String[] args) {
        StackOverflow stackOverflow = new StackOverflow() ;
        stackOverflow.addUsers();

        User bob = stackOverflow.addUser("Bob") ;
        User charlie = stackOverflow.addUser("Charlie") ;
        User stan = stackOverflow.addUser("Stan") ;

        
        List<Tag> tags = new ArrayList<Tag>(Arrays.asList(
            new Tag("java"),
            new Tag("oops")
        )) ;
        // Bob ask question
        Question javaQuestion = stackOverflow.askQuestion(
            "What is java programming", 
            "can you please explain all pillers of oops programming ?", 
            bob, 
            tags
        ) ;

        // Charlie askquestion
        Question reactQuestion = stackOverflow.askQuestion(
            "What is React.js", 
            "can you please list down all the properties?", 
            charlie, 
            tags
        ) ;

        // charlie asnwer the bob's question
        Answer answer = stackOverflow.answerQuestion(charlie, javaQuestion, 
            "There are 4 main pillers of OOPS. 1) Polymorphism 2) Inheritance 3) Abstraction 4) Encapsulation"
        );

        // vote to javaQuestion by stan
        stackOverflow.voteQuestion(javaQuestion, stan, 1);

        stackOverflow.printData() ;
    }
}
