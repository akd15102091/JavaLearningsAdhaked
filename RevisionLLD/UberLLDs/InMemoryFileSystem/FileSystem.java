package UberLLDs.InMemoryFileSystem;

import java.util.LinkedList;

public class FileSystem {
    // Root and current working directory
    private final Dir root;
    private Dir current;

    public FileSystem() {
        root = new Dir("/", null);
        current = root;
    }

    // -------------- mkdir --------------
    // Creates directory along a given path
    public void mkdir(String path) {
        String[] parts = path.split("/");
        Dir node = path.startsWith("/") ? root : current;

        for (String p : parts) {
            if (p.isEmpty() || p.equals(".")) continue;

            if (p.equals("..")) {
                if (node.parent != null) node = node.parent;
                continue;
            }

            node.children.putIfAbsent(p, new Dir(p, node));
            node = node.children.get(p);
        }
    }


    // -------------- pwd --------------
    public String pwd() {
        if (current == root) return "/";

        LinkedList<String> names = new LinkedList<>();
        Dir node = current;

        while (node != null && node != root) {
            names.addFirst(node.name);
            node = node.parent;
        }
        return "/" + String.join("/", names);
    }


    // -------------- cd --------------
    // Changes directory, supports *, ., ..
    public boolean cd(String path) {
        String[] parts = path.split("/");
        Dir node = path.startsWith("/") ? root : current;

        for (String p : parts) {
            if (p.isEmpty() || p.equals(".")) continue;

            if (p.equals("..")) {
                if (node.parent != null) node = node.parent;
                continue;
            }

            if (p.equals("*")) {
                if (node.children.isEmpty()) return false;

                // deterministic behavior → choose lexicographically smallest child
                String smallest = node.children.keySet()
                        .stream()
                        .sorted()
                        .findFirst()
                        .get();

                node = node.children.get(smallest);
                continue;
            }

            if (!node.children.containsKey(p)) return false;
            node = node.children.get(p);
        }

        current = node;
        return true;
    }
}
