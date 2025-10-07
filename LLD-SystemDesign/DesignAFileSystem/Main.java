package DesignAFileSystem;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        fs.createFile("/docs/design.txt");
        fs.writeFile("/docs/design.txt", "L1 Design");
        fs.writeFile("/docs/design.txt", "L2 Design");

        System.out.println("Latest Content: " + fs.readFile("/docs/design.txt"));
        System.out.println("Metadata: " + fs.getFileMetadata("/docs/design.txt"));

        fs.rename("/docs/design.txt", "design_updated.txt");
        fs.move("/docs/design_updated.txt", "/");

        System.out.println("Root files after move: " + fs.listFiles("/"));
    }
}
