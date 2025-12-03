package UberLLDs.InMemoryFileSystem;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        fs.mkdir("a/b/c");
        System.out.println(fs.pwd()); // "/"

        fs.cd("a/b");
        System.out.println(fs.pwd()); // "/a/b"

        fs.cd("..");
        System.out.println(fs.pwd()); // "/a"

        fs.cd("b/c");
        System.out.println(fs.pwd()); // "/a/b/c"

        // wildcard example
        fs.cd("*");
        System.out.println(fs.pwd()); // still "/a/b/c" (no child below c)

        // add children
        fs.mkdir("x");
        fs.mkdir("y");

        fs.cd("/a");
        fs.cd("*"); // goes to lexicographically smallest child: "b"
        System.out.println(fs.pwd()); // "/a/b"
    }
}
