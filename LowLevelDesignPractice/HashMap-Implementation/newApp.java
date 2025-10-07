
/*
 * Click `Run` to execute the snippet below!
 */

 import java.io.*;
 import java.util.*;
 
 /*
  * To execute Java, please define "static void main" on a class
  * named Solution.
  *
  * If you need more classes, simply define them inline.
  */

 
 
  class Person{
    private String firstName;
    private String lastName;
    private int age;
 
    public Person(String firstName, String lastName, int age){
       this.firstName = firstName;
       this.lastName = lastName;
       this.age =age;
    } 
 
 
 }
 class newApp {
    
   public static void main(String[] args) {
     ArrayList<String> strings = new ArrayList<String>();
     Map<Person,String>mp = new java.util.HashMap<>();
     Person p1 = new Person("john","Wick",25);
     Person p2 = new Person("john","Wick",25);

     mp.put(p1,"john");
     mp.put(p2,"john");
     System.out.println(mp.size());
     
     System.out.println(p1.equals(p2));
     System.out.println(p1==p2);

     String s1 = new String("new");
     String s2 = new String("new");
     System.out.println(s1.equals(s2));
     System.out.println(s1==s2);

     strings.add("Hello, World!");
     strings.add("Welcome to CoderPad.");
     strings.add("This pad is running Java " + Runtime.version().feature());
 
     for (String string : strings) {
       System.out.println(string);
     }
   }
 }
 
 
 // Your previous Plain Text content is preserved below:
 
 // This is just a simple shared plaintext pad, with no execution capabilities.
 
 // When you know what language you'd like to use for your interview,
 // simply choose it from the dots menu on the tab, or add a new language
 // tab using the Languages button on the left.
 
 // You can also change the default language your pads are created with
 // in your account settings: https://app.coderpad.io/settings
 
 // Enjoy your interview!
 
 // Person -> firstName, lastName, age
 
 // Person p1, Person p2, Person p3
 
//  Person p1 = new Person("John", "Wick", 46);
//  Person p2 = new Person("John", "Wick", 46);
// }