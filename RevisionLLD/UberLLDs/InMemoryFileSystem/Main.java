package UberLLDs.InMemoryFileSystem;

public class Main {
    public static void main(String[] args) {

        FileSystem fs = new FileSystem();

        // ---- Basic mkdir + pwd ----
        fs.mkdir("a/b/c");
        System.out.println(fs.pwd());       // /

        // ---- Normal cd ----
        fs.cd("a/b");
        System.out.println(fs.pwd());       // /a/b

        fs.cd("..");
        System.out.println(fs.pwd());       // /a

        // ---- Wildcard simple ----
        fs.cd("b/c");
        System.out.println(fs.pwd());       // /a/b/c

        // Add children to demonstrate wildcard child selection
        fs.mkdir("x");
        fs.mkdir("y");

        // ---- Wildcard '*' tries ., .., children ----
        fs.cd("*");
        System.out.println(fs.pwd());       // stays at /a/b/c ('.' matches first)

        // ---- Nested wildcard ----
        fs.cd("/");
        fs.cd("a/*/c");    
        System.out.println(fs.pwd());       // /a/b/c

        // ---- Wildcard with parent usage ----
        fs.cd("*/..");
        System.out.println(fs.pwd());       // /a/b
    }
}
